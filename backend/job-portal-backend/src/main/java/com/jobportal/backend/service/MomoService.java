    package com.jobportal.backend.service;

    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.util.Map;
    import java.util.HashMap;

    import javax.crypto.Mac;
    import javax.crypto.spec.SecretKeySpec;

    import java.nio.charset.StandardCharsets;

    @Service
    public class MomoService {

        private final String partnerCode = "MOMO";
        private final String accessKey = "F8BBA842ECF85";
        private final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";

        private final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";

        public String createPayment(Long amount, String orderId, Long companyId, Long packageId) throws Exception {

    String requestId = orderId;
    String orderInfo = "Thanh toán gói";

    String redirectUrl = "http://localhost:3000/payment-success";
    String ipnUrl = "https://abc123.ngrok-free.app/momo/ipn";

    String extraData = java.util.Base64.getEncoder()
            .encodeToString(("companyId=" + companyId + "&packageId=" + packageId).getBytes());

    String rawHash = "accessKey=" + accessKey +
            "&amount=" + amount +
            "&extraData=" + extraData +
            "&ipnUrl=" + ipnUrl +
            "&orderId=" + orderId +
            "&orderInfo=" + orderInfo +
            "&partnerCode=" + partnerCode +
            "&redirectUrl=" + redirectUrl +
            "&requestId=" + requestId +
            "&requestType=payWithATM";

    String signature = hmacSHA256(rawHash, secretKey);

    Map<String, String> payload = new HashMap<>();
    payload.put("partnerCode", partnerCode);
    payload.put("accessKey", accessKey);
    payload.put("requestId", requestId);
    payload.put("amount", amount.toString());
    payload.put("orderId", orderId);
    payload.put("orderInfo", orderInfo);
    payload.put("redirectUrl", redirectUrl);
    payload.put("ipnUrl", ipnUrl);
    payload.put("extraData", extraData);
    payload.put("requestType", "payWithATM");
    payload.put("signature", signature);
    payload.put("lang", "vi");
    payload.put("orderType", "momo_wallet");
    payload.put("autoCapture", "true");

    RestTemplate restTemplate = new RestTemplate();
    Map res = restTemplate.postForObject(endpoint, payload, Map.class);

    System.out.println("MOMO RESPONSE: " + res);

    if (res == null || res.get("payUrl") == null) {
        throw new RuntimeException("Momo error: " + res);
    }

    return res.get("payUrl").toString();
}

        private String hmacSHA256(String data, String key) throws Exception {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        }
}