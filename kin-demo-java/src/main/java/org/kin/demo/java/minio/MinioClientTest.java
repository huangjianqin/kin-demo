package org.kin.demo.java.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjianqin
 * @date 2024/1/6
 */
public class MinioClientTest {
    private static final String ACCESS_KEY = "c7lOXUARMI7xDVq5qzRw";
    private static final String SECRET_KEY = "NrI4ZiAsoNOW3QoRlr07fpICttPVUK1mYelUYUjr";
    private static final String TEST_BUCKET = "test";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        MinioClient minioClient = null;
        try {
            //创建minio client
            minioClient =
                    MinioClient.builder()
                            .endpoint("http://127.0.0.1:9000")
                            .credentials(ACCESS_KEY, SECRET_KEY)
                            .build();
            String objectName = "Alfred Backup 2023-09-09.tar.gz";
//            upload(minioClient, TEST_BUCKET, objectName, "/Users/hjq/Desktop/Alfred Backup 2023-09-09.tar.gz");
//            System.out.println("DownloadUrl: " + getDownloadUrl(minioClient, TEST_BUCKET, objectName));
//            System.out.println("UploadUrl: " + getUploadUrl(minioClient, TEST_BUCKET, objectName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void upload(MinioClient minioClient,
                               String bucketName,
                               String objectName,
                               String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        try {


            //判断bucket是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                System.out.println(String.format("bucket '%s' is not exists, going to create it!", bucketName));
                //create bucket if not exists
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println(String.format("bucket '%s' already exists!", bucketName));
            }

            //upload
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(filePath)
                            .build());
            System.out.println(String.format("'%s' is successfully uploaded as object '%s' to bucket '%s'", filePath, objectName, bucketName));
        } catch (MinioException e) {
            e.printStackTrace();
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }

    private static String getDownloadUrl(MinioClient minioClient,
                                         String bucketName,
                                         String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Map<String, String> reqParams = new HashMap<>(2);
        reqParams.put("response-content-type", "application/json");

        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(1, TimeUnit.DAYS)
                        .extraQueryParams(reqParams)
                        .build());
    }

    private static String getUploadUrl(MinioClient minioClient,
                                       String bucketName,
                                       String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(20, TimeUnit.MINUTES)
                        .build());
        return url;
    }
}
