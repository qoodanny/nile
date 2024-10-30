package comp3350.nilebookstore.tests.business.payment;

//no dependencies to be mocked using Mockito
//import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.nilebookstore.business.payment.PaymentVerification;

public class PaymentVerificationTest {
	private PaymentVerification paymentVerification;

	@Test
	public void testVerifyCardNum() {
		System.out.println("Starting testVerifyCardNum...");
		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 123);
		assertTrue(paymentVerification.verifyCardNum());

		paymentVerification = new PaymentVerification("1234 5678 1234 567", 12, 2030, 123);
		assertFalse(paymentVerification.verifyCardNum());

		paymentVerification = new PaymentVerification("1234 5678 1234 56X8", 12, 2030, 123);
		assertFalse(paymentVerification.verifyCardNum());
		System.out.println("Finished testVerifyCardNum.");
	}

	@Test
	public void testVerifyExpMonth() {
		System.out.println("Starting testVerifyExpMonth...");
		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 123);
		assertTrue(paymentVerification.verifyExpMonth());

		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 0, 2030, 123);
		assertFalse(paymentVerification.verifyExpMonth());

		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 13, 2030, 123);
		assertFalse(paymentVerification.verifyExpMonth());
		System.out.println("Finished testVerifyExpMonth.");
	}

	@Test
	public void testVerifyExpYear() {
		System.out.println("Starting testVerifyExpYear...");
		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 123);
		assertTrue(paymentVerification.verifyExpYear());

		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2000, 123);
		assertFalse(paymentVerification.verifyExpYear());
		System.out.println("Finished testVerifyExpYear.");
	}

	@Test
	public void testVerifyCardCVC() {
		System.out.println("Starting testVerifyCardCVC...");
		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 123);
		assertTrue(paymentVerification.verifyCardCVC());

		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 12);
		assertFalse(paymentVerification.verifyCardCVC());

		paymentVerification = new PaymentVerification("1234 5678 1234 5678", 12, 2030, 1234);
		assertFalse(paymentVerification.verifyCardCVC());
		System.out.println("Finished testVerifyCardCVC.");
	}
}
