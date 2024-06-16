package com.payment.stripe.payment.stripe.service;

import com.payment.stripe.payment.stripe.config.StripeKeys;
import com.payment.stripe.payment.stripe.config.SubscriptionProperties;
import com.payment.stripe.payment.stripe.dto.PaymentInfoResponse;
import com.payment.stripe.payment.stripe.dto.PaymentResponse;
import com.payment.stripe.payment.stripe.entity.PaymentStatus;
import com.payment.stripe.payment.stripe.entity.TotalPayedSubscriptionsCountEntity;
import com.payment.stripe.payment.stripe.entity.UserSubscriptionEntity;
import com.payment.stripe.payment.stripe.events.ListenersEventType;
import com.payment.stripe.payment.stripe.events.PaymentEvent;
import com.payment.stripe.payment.stripe.exception.CreatedPaymentUserMismatchException;
import com.payment.stripe.payment.stripe.exception.StripePaymentSessionNotFoundException;
import com.payment.stripe.payment.stripe.exception.StripeSessionCreationExceptionException;
import com.payment.stripe.payment.stripe.subscription.SubscriptionFetcher;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentService implements PaymentService {

    private final StripeKeys keys;
    private final ApplicationEventPublisher publisher;
    private final SubscriptionProperties subscriptionProperties;
    private SubscriptionFetcher subscriptionFetcher;

    @Override
    public PaymentInfoResponse getInfo() {
        final int totalPayedCount = 1000;
        final int availableCount =
                subscriptionProperties.getEarlyBirdLimit() > totalPayedCount
                        ? subscriptionProperties.getEarlyBirdLimit() - totalPayedCount
                        : 0;

        return PaymentInfoResponse.builder()
                .earlyBirdPrice(subscriptionProperties.getEarlyBirdPrice())
                .usualPrice(subscriptionProperties.getUsualPrice())
                .availableCount(availableCount)
                .earlyBirdCount(subscriptionProperties.getEarlyBirdLimit())
                .pageLimitTrial(subscriptionProperties.getPageLimitTrial())
                .pageLimitPremium(subscriptionProperties.getPageLimitSubscribed())
                .trialDocCount(subscriptionProperties.getTrialDocCount())
                .premiumDocCount(subscriptionProperties.getSubscribedDocCount())
                .build();
    }

    @Override
    public TotalPayedSubscriptionsCountEntity incrementTotalPayedCount() {
        var entity = new TotalPayedSubscriptionsCountEntity();
        final int incrementedCount = entity.getTotalCount() + 1;
        entity.setTotalCount(incrementedCount);
        // you can save entity your database.
        return entity;
    }

    @Override
    public PaymentResponse pay() {
        final int totalPayedCount = 100;
        final boolean isEarlyBird = totalPayedCount < subscriptionProperties.getEarlyBirdLimit();
        final UUID userId = UUID.randomUUID();
        final SessionCreateParams params = buildSessionParams(userId, isEarlyBird);
        final String redirectURI = _safeSession(params).getUrl();

        return new PaymentResponse(redirectURI);
    }

    @Override
    public void onSuccess(String sessionId) {
        final Session session = _safeRetrieve(sessionId);
        final UUID userId = UUID.randomUUID();
        final String email = "customer@mail.com";

        if (!email.equals(session.getCustomerEmail())) {
            throw new CreatedPaymentUserMismatchException();
        }
        final UserSubscriptionEntity instance = subscriptionFetcher.fetch(userId);
        if (instance.getPaymentStatus() == PaymentStatus.PAYED) {
            return;
        }

        publisher.publishEvent(new PaymentEvent(userId, PaymentStatus.PENDING, ListenersEventType.PAYMENT_ACCEPTED));
    }

    @Override
    public void onCancel(String sessionId) {

    }


    private SessionCreateParams buildSessionParams(final UUID userId, boolean isEarlyBird) {

        return new SessionCreateParams.Builder()
                .addLineItem(new SessionCreateParams.LineItem.Builder()
                        .setQuantity(1l)
                        .setPrice(isEarlyBird ? keys.earlyBirdPriceId : keys.priceId)
                        .build())
                .setCustomerEmail("Customer@mail.com")
                .setSuccessUrl(keys.successURL)
                .setCancelUrl(keys.cancelURL)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .build();
    }

    private Session _safeSession(final SessionCreateParams from) {
        try {
            return Session.create(from);
        } catch (StripeException e) {
            log.error("Unable to create Stripe session ...", e);
            throw new StripeSessionCreationExceptionException();
        }
    }

    private Session _safeRetrieve(String sessionId) {
        try {
            return Session.retrieve(sessionId);
        } catch (StripeException e) {
            log.error("Unable to find Stripe session with id : " + sessionId, e);
            throw new StripePaymentSessionNotFoundException();
        }
    }
}
