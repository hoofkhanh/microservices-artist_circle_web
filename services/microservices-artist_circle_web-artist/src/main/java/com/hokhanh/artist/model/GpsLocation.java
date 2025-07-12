package com.hokhanh.artist.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class GpsLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private float longitude;
	private float latitude;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public GpsLocation(GpsLocation gps) {
	    if (gps == null) {
	        this.id = null;
	        this.longitude = 0;
	        this.latitude = 0;
	        this.updatedAt = null;
	        return;
	    }
	    this.id = gps.id;
	    this.longitude = gps.longitude;
	    this.latitude = gps.latitude;
	    this.updatedAt = gps.updatedAt;
	}

}
