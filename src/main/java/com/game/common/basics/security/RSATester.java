package com.game.common.basics.security;




public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
//            Map<String, Object> keyMap = RSAUtils.genKeyPair();
//            publicKey = RSAUtils.getPublicKey(keyMap);
//            privateKey = RSAUtils.getPrivateKey(keyMap);
            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN1LajLKJJ62/Rf2MLz4XW9EhNyN9t9Yvor2IN8E39MHceIwe2ASJ+J9QIk8KHGUn6LIsDYO9dvhI9NzT0ZO18yC4JcTrIWbSBeejh/4jNeGlyG0TqOOfWLpFXEX9APG9LLxETPZtVJRZJAGg6a9tp4PKPw3fxjf0VwhWkZ3BSyxAgMBAAECgYB/JbCcBAcsZilfViXEvdM5dh38d1kMFPpN74MNgMh1gMaPVpncpjHicWLeNwFp2SIrrrGILepXbrFd8dfvpqdYGastr1l+KpImBG4AeHglSa+LbfySEUCbk2JNwqdoyJy0ra5xP8FSnZR3cfLiLJI474X4RFfvRWouUCdTGXqZPQJBAO5oxjD8Z3auqZSlJx4qpflt5mMtxvQisAndB2kqZ+FojKaiMXNkH5Sic5CDgFPsN/mn3rkRHmFbNaRNSbErCdcCQQDtn14bx/avBBhoJtG+l/jMUiUKi6/uRyroUVJgqEJP03wlr8JnR1g0dAeLtMfCsVlye3CY75NL0p6W9KrNy3y3AkEA2+wMv01mh8WRK0y57Ar9djzZ5K1qtPR2nXKmDm4Oy5DtBh3iLgazVyIQJsQXkdL6w7MGMoPHGZnQvtKI4wTHmwJBAJXKrcdzgXUIbt5RAk1UfblHWyRPtELPyCOVCAn4Z3zsw0j6h+EVH2xJbGxMNNTteYZqfCtccw0MI37jkzyRtvcCQFHwRKAt7na+tFe6o8N9YoM+x7IC/Z0632HFpfDQOoBHW17FNichIEUqP4na8KxRJJvV9mS8jhuHJGjUjzBYW5Y=";
            publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdS2oyyiSetv0X9jC8+F1vRITcjfbfWL6K9iDfBN/TB3HiMHtgEififUCJPChxlJ+iyLA2DvXb4SPTc09GTtfMguCXE6yFm0gXno4f+IzXhpchtE6jjn1i6RVxF/QDxvSy8REz2bVSUWSQBoOmvbaeDyj8N38Y39FcIVpGdwUssQIDAQAB";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {
        test();
        //testSign();
//    	base64test();
    	//simple();
    }

    static void test() throws Exception {
    	System.out.println(Base64Utils.encode("123".getBytes()));
    	//String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdS2oyyiSetv0X9jC8+F1vRITcjfbfWL6K9iDfBN/TB3HiMHtgEififUCJPChxlJ+iyLA2DvXb4SPTc09GTtfMguCXE6yFm0gXno4f+IzXhpchtE6jjn1i6RVxF/QDxvSy8REz2bVSUWSQBoOmvbaeDyj8N38Y39FcIVpGdwUssQIDAQAB";
    	String privKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN1LajLKJJ62/Rf2MLz4XW9EhNyN9t9Yvor2IN8E39MHceIwe2ASJ+J9QIk8KHGUn6LIsDYO9dvhI9NzT0ZO18yC4JcTrIWbSBeejh/4jNeGlyG0TqOOfWLpFXEX9APG9LLxETPZtVJRZJAGg6a9tp4PKPw3fxjf0VwhWkZ3BSyxAgMBAAECgYB/JbCcBAcsZilfViXEvdM5dh38d1kMFPpN74MNgMh1gMaPVpncpjHicWLeNwFp2SIrrrGILepXbrFd8dfvpqdYGastr1l+KpImBG4AeHglSa+LbfySEUCbk2JNwqdoyJy0ra5xP8FSnZR3cfLiLJI474X4RFfvRWouUCdTGXqZPQJBAO5oxjD8Z3auqZSlJx4qpflt5mMtxvQisAndB2kqZ+FojKaiMXNkH5Sic5CDgFPsN/mn3rkRHmFbNaRNSbErCdcCQQDtn14bx/avBBhoJtG+l/jMUiUKi6/uRyroUVJgqEJP03wlr8JnR1g0dAeLtMfCsVlye3CY75NL0p6W9KrNy3y3AkEA2+wMv01mh8WRK0y57Ar9djzZ5K1qtPR2nXKmDm4Oy5DtBh3iLgazVyIQJsQXkdL6w7MGMoPHGZnQvtKI4wTHmwJBAJXKrcdzgXUIbt5RAk1UfblHWyRPtELPyCOVCAn4Z3zsw0j6h+EVH2xJbGxMNNTteYZqfCtccw0MI37jkzyRtvcCQFHwRKAt7na+tFe6o8N9YoM+x7IC/Z0632HFpfDQOoBHW17FNichIEUqP4na8KxRJJvV9mS8jhuHJGjUjzBYW5Y=";
        //String source = "dddddddddddddddddffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffddddddddddddddddddddddddddddddddddddddddddddddddddddduserName=nnniiieee&password=111111&ck=ypQNMeudgrTCqDSlRuDDSpZ5YuS7ssssssssssssssssssssssssssssssssssssssssssssssssssssss";
        //byte[] data = source.getBytes();
        //byte[] encodedData = RSAUtils.encryptByPublicKey(data, pubKey);
        //String mi = Base64Utils.encode(encodedData);
        String mi = "TGuAfVlITB9LNq1aqS68Nr3TZCDEUF7y0AZsGQiQJZsGXD22ro9nsD6SH6dwDxFfPjRvDdowEsUJJrUVQXOO/3LoqCwT956NYJdCJlplFcga3oCtrq8u3XpExMQ5gGVPhWbMXeBDLT1s3rwnlMXRjDp7hPZn3mOxeSkk1CFjoUI=";
        System.out.println("加密后文字：\r\n" + mi);
        long s = System.currentTimeMillis();
        byte[] miByte = Base64Utils.decode(mi);
        byte[] decodedData = RSAUtils.decryptByPrivateKey(miByte, privKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
   	 	System.out.println((System.currentTimeMillis() - s));
//   	 	Map<String, Object> ss = new HashMap<String, Object>();
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "kevin";
        byte[] data = source.getBytes();
        System.out.println(Base64Utils.encode(data));
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
        System.out.println(String.valueOf(1.22d));
    }
    static void simple() throws Exception{
    	String signature = "a2V2aW4=";
    	System.out.println(verify(signature,publicKey,privateKey));
    }
    public static boolean verify(String signature, String publicKey, String privateKey) throws Exception{
    	byte[] data = Base64Utils.decode(signature);
    	byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
    	String sign = RSAUtils.sign(encodedData, privateKey);
    	boolean status = RSAUtils.verify(encodedData, publicKey, sign);
    	return status;
    }
}
