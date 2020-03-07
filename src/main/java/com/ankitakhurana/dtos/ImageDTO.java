package com.ankitakhurana.dtos;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ankitakhurana.models.Image;
import com.ankitakhurana.models.User;
import com.ankitakhurana.services.ImageService;

public class ImageDTO {
	
		public List<String> getImageList(User user) {
			List<Image> list = ImageService.getImages(user);
			List<String> listToDisplay = new ArrayList<String>();
			for (Image image : list) {
				byte[] imageData = image.getImage();
				listToDisplay.add(Base64.getMimeEncoder().encodeToString(imageData));
			}
			
			return listToDisplay;
		}

		public List<String> getImageName(User user) {
			List<Image> list = ImageService.getImages(user);
			List<String> listToDisplay = new ArrayList<String>();
			for (Image image : list) {
				listToDisplay.add(image.getName());
			}
			return listToDisplay;
		}

		public List<Long> getImageNumber(User user) {
			List<Image> list = ImageService.getImages(user);
			List<Long> listToDisplay = new ArrayList<Long>();
			for (Image image : list) {
				listToDisplay.add(image.getId());
			}
			return listToDisplay;
		}

		public List<Double> getImageSize(User user) {
			List<Image> list = ImageService.getImages(user);
			List<Double> listToDisplay = new ArrayList<Double>();
			for (Image image : list) {
				listToDisplay.add(image.getSize());
			}
			return listToDisplay;
		}
	

}
