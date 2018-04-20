package com.juunew.admin.services;

import cn.ucloud.ufile.UFileClient;
import cn.ucloud.ufile.UFileConfig;
import cn.ucloud.ufile.UFileRequest;
import cn.ucloud.ufile.UFileResponse;
import cn.ucloud.ufile.sender.PutSender;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by guange on 26/05/2017.
 */
@Service
public class CloudFileService {

    private static Logger logger = LoggerFactory.getLogger(CloudFileService.class);

    @Value("${ucloud.publicKey}")
    String ucloudPublicKey;

    @Value("${ucloud.privateKey}")
    String ucloudPrivateKey;

    @Value("${ucloud.proxySuffix}")
    String ucloudProxySuffix;

    @Value("${ucloud.downloadProxySuffix}")
    String ucloudDownloadProxySuffix;

    public void putFile(String filePath, String prefix) throws Exception {
        UFileConfig.getInstance().setUcloudPublicKey(ucloudPublicKey);
        UFileConfig.getInstance().setUcloudPrivateKey(ucloudPrivateKey);
        UFileConfig.getInstance().setProxySuffix(ucloudProxySuffix);
        UFileConfig.getInstance().setDownloadProxySuffix(ucloudDownloadProxySuffix);


        File file = new File(filePath);

        String bucketName = "yxx";
        String key = prefix + file.getName();

        UFileRequest request = new UFileRequest();
        request.setBucketName(bucketName);
        request.setKey(key);

        request.setInputStream(new FileInputStream(filePath));
        request.setContentLength(file.length());

        UFileClient ufileClient = new UFileClient();
        putFile(ufileClient, request);
        ufileClient.shutdown();

    }

    private static boolean putFile(UFileClient ufileClient, UFileRequest request) {
        PutSender sender = new PutSender();
        sender.makeAuth(ufileClient, request);

        UFileResponse response = sender.send(ufileClient, request);

        if (response != null) {

            if(response.getStatusLine().getStatusCode()==200){
                return true;
            }

            logger.debug("status line: {}", response.getStatusLine());

            Header[] headers = response.getHeaders();
            for (int i = 0; i < headers.length; i++) {
//                logger.debug("header {}:{}" , headers[i].getName(), headers[i].getValue());
            }

            logger.debug("body length: {}",response.getContentLength());

            InputStream inputStream = response.getContent();
            if (inputStream != null) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String s = "";
                    while ((s = reader.readLine()) != null) {
                        logger.debug(s);
                    }

                } catch (IOException e) {
                    logger.error("上传失败",e);
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return false;
    }
}
