package junit;

import com.game.common.basics.security.Base64Utils;
import com.game.common.basics.security.RSAUtils;




public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN1LajLKJJ62/Rf2MLz4XW9EhNyN9t9Yvor2IN8E39MHceIwe2ASJ+J9QIk8KHGUn6LIsDYO9dvhI9NzT0ZO18yC4JcTrIWbSBeejh/4jNeGlyG0TqOOfWLpFXEX9APG9LLxETPZtVJRZJAGg6a9tp4PKPw3fxjf0VwhWkZ3BSyxAgMBAAECgYB/JbCcBAcsZilfViXEvdM5dh38d1kMFPpN74MNgMh1gMaPVpncpjHicWLeNwFp2SIrrrGILepXbrFd8dfvpqdYGastr1l+KpImBG4AeHglSa+LbfySEUCbk2JNwqdoyJy0ra5xP8FSnZR3cfLiLJI474X4RFfvRWouUCdTGXqZPQJBAO5oxjD8Z3auqZSlJx4qpflt5mMtxvQisAndB2kqZ+FojKaiMXNkH5Sic5CDgFPsN/mn3rkRHmFbNaRNSbErCdcCQQDtn14bx/avBBhoJtG+l/jMUiUKi6/uRyroUVJgqEJP03wlr8JnR1g0dAeLtMfCsVlye3CY75NL0p6W9KrNy3y3AkEA2+wMv01mh8WRK0y57Ar9djzZ5K1qtPR2nXKmDm4Oy5DtBh3iLgazVyIQJsQXkdL6w7MGMoPHGZnQvtKI4wTHmwJBAJXKrcdzgXUIbt5RAk1UfblHWyRPtELPyCOVCAn4Z3zsw0j6h+EVH2xJbGxMNNTteYZqfCtccw0MI37jkzyRtvcCQFHwRKAt7na+tFe6o8N9YoM+x7IC/Z0632HFpfDQOoBHW17FNichIEUqP4na8KxRJJvV9mS8jhuHJGjUjzBYW5Y=";
            publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdS2oyyiSetv0X9jC8+F1vRITcjfbfWL6K9iDfBN/TB3HiMHtgEififUCJPChxlJ+iyLA2DvXb4SPTc09GTtfMguCXE6yFm0gXno4f+IzXhpchtE6jjn1i6RVxF/QDxvSy8REz2bVSUWSQBoOmvbaeDyj8N38Y39FcIVpGdwUssQIDAQAB";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {
    	String privKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN1LajLKJJ62/Rf2MLz4XW9EhNyN9t9Yvor2IN8E39MHceIwe2ASJ+J9QIk8KHGUn6LIsDYO9dvhI9NzT0ZO18yC4JcTrIWbSBeejh/4jNeGlyG0TqOOfWLpFXEX9APG9LLxETPZtVJRZJAGg6a9tp4PKPw3fxjf0VwhWkZ3BSyxAgMBAAECgYB/JbCcBAcsZilfViXEvdM5dh38d1kMFPpN74MNgMh1gMaPVpncpjHicWLeNwFp2SIrrrGILepXbrFd8dfvpqdYGastr1l+KpImBG4AeHglSa+LbfySEUCbk2JNwqdoyJy0ra5xP8FSnZR3cfLiLJI474X4RFfvRWouUCdTGXqZPQJBAO5oxjD8Z3auqZSlJx4qpflt5mMtxvQisAndB2kqZ+FojKaiMXNkH5Sic5CDgFPsN/mn3rkRHmFbNaRNSbErCdcCQQDtn14bx/avBBhoJtG+l/jMUiUKi6/uRyroUVJgqEJP03wlr8JnR1g0dAeLtMfCsVlye3CY75NL0p6W9KrNy3y3AkEA2+wMv01mh8WRK0y57Ar9djzZ5K1qtPR2nXKmDm4Oy5DtBh3iLgazVyIQJsQXkdL6w7MGMoPHGZnQvtKI4wTHmwJBAJXKrcdzgXUIbt5RAk1UfblHWyRPtELPyCOVCAn4Z3zsw0j6h+EVH2xJbGxMNNTteYZqfCtccw0MI37jkzyRtvcCQFHwRKAt7na+tFe6o8N9YoM+x7IC/Z0632HFpfDQOoBHW17FNichIEUqP4na8KxRJJvV9mS8jhuHJGjUjzBYW5Y=";
        String data = "Fmaf3s59yFkylx2b7fr7Jc7IiTnD9aScC1ow9K9qxXoiS4sidxS3X2tWWaKjagKlSFMtCH+kaeDOe7ktWuUwrlhslXT8RZ+r1GO3vCyBwYkybvNVdg5VpeQd1cPJaQyv/p7HO0VJgu5++4ghLsIedNrwdh7tdaTiJFd1SLtfAjOKs9Ho/6q3g7+QL28e2l/MHeJJWwWbeSnCUuOu5Uz9Ir0zqv+HYw99BtIVIN5dxM/TetJOaj2i0GZOWLV0zICJtdFvweZ4Cza5EPg/CEVB73pPudY0efC5Z1v4808hQcJoNR+ceNqYH1bqAmyp+9FPVOMVBA5OtxBzbzfm0tnq7A==";
        data = data.replaceAll(" ", "+");
        byte[] miByte = Base64Utils.decode(data);
        byte[] decodedData = RSAUtils.decryptByPrivateKey(miByte, privKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

}
