package com.example.customer.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageOptions {

	public static final String CUSTOMER_UPLOAD_FOLDER = "images/customer/";

	public String savePinture(MultipartFile multipartFile, Long idCustomer) {

		String pinture = "";

		try {

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);

			String fileName = String.valueOf(idCustomer) + "-picture-" + dateName + "."
					+ multipartFile.getContentType().split("/")[1];

			log.info("ruta de archivo : {}", fileName);

			byte[] bs = multipartFile.getBytes();
			Path path = Paths.get(CUSTOMER_UPLOAD_FOLDER + fileName);
			Files.write(path, bs);

			// return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bs);
			return pinture = CUSTOMER_UPLOAD_FOLDER + fileName;

		} catch (Exception e) {

			e.printStackTrace();
			return pinture;
			// return ResponseEntity(CustomErrorType(multipartFile.getOriginalFilename()),
			// HttpStatus.CONFLICT);
		}

	}

	public void removePinture(String filemane) {

		try {
			log.info("Eliminar archivo : {}", filemane);
			Path path = Paths.get(filemane);
			File file = path.toFile();
			log.info("Eliminar ruta : {}", file);
			if (file.exists()) {
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
