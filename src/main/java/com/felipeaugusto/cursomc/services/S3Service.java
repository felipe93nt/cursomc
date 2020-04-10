package com.felipeaugusto.cursomc.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private Logger log = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	public void uploadFile(String localFilePath) {
		try {
			File file = new File(localFilePath);
			log.info("Iniciando upload");
			s3Client.putObject(new PutObjectRequest(bucketName, "teste", file));
			log.info("upload finalizado");
		} catch (AmazonServiceException e) {
			log.info("AmazonServiceException: " + e.getErrorMessage());
			log.info("Status code: " + e.getStatusCode());
		} catch (AmazonClientException e) {
			log.info("AmazonClientException: " + e.getMessage());
		}
	}
}
