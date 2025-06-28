package subsystem.vnpay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import entity.payment.PaymentTransaction;
import exception.FailedTransactionException;

public class Response {
	public PaymentTransaction processResponse(String query) throws Exception{
		Map<String, String> params = parseQueryString(query);
		return handleErrorCode(params);
	}
	
	private Map<String, String> parseQueryString(String query){
		Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
	}
	
	private PaymentTransaction handleErrorCode(Map<String, String> params) throws FailedTransactionException {
		if (params == null) {
            return null;
        }
        // Create Payment transaction
        String errorCode = params.get("vnp_ResponseCode");
        String transactionId = params.get("vnp_TransactionNo");
        String transactionContent = params.get("vnp_OrderInfo");
        int amount = Integer.parseInt((String) params.get("vnp_Amount")) / 100000;
        String createdAt = params.get("vnp_PayDate");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        PaymentTransaction trans;
        
        System.out.print(errorCode);
        switch (errorCode) {
            case "00":
            	System.out.println("da thanh toan thanh cong");
            	System.out.println(createdAt);
            	trans = new PaymentTransaction(transactionId, transactionContent, amount, LocalDateTime.parse(createdAt, formatter).toLocalDate());
            	break;
            case "07":
            	throw new FailedTransactionException("Suspicious transaction");
            case "09":
            	throw new FailedTransactionException("Have not registered Internet Banking");
            case "10":
            	throw new FailedTransactionException("Fail verification 3 times");
            case "11":
            	throw new FailedTransactionException("Payment deadline has expired");
            case "12":
            	throw new FailedTransactionException("Your account is locked");
            case "13":
            	throw new FailedTransactionException("Wrong OTP");
            case "24":
            	throw new FailedTransactionException("Transaction is canceled");
            case "51":
            	throw new FailedTransactionException("Not enough money in account");
            case "65":
            	throw new FailedTransactionException("Account exceeds the limit");
            case "75":
            	throw new FailedTransactionException("Bank is in maintenance");
            case "79":
            	throw new FailedTransactionException("Too many password trials");
            default:
            	throw new FailedTransactionException("Other reasons");
        }
        return trans;
	}
	 
		
}
