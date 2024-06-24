package com.andre.dojo.Controller;

import io.javalin.http.Handler;
import static com.andre.dojo.dataModel.Payment.getAllPayment;

public class PaymentController {
    public static Handler getAllPayments = ctx -> ctx.result(getAllPayment());
}
