package com.ankitakhurana.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Sno")
	private long id;

	@Column(name = "ImageName", nullable = false)
	private String name;

	@Lob
	@Column(name = "ImageType", nullable = false, columnDefinition = "mediumblob")
	private byte[] image;

	@ManyToOne
	@JoinColumn(name = "User_id", nullable = false)
	private User user;

	@Column(name = "ImageSize", nullable = false)
	private double size;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = Math.floor(100 * size) / 100;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
