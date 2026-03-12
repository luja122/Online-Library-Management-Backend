package com.example.Online.Library.Management.System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Books {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Integer id;
public String title;
public String author;
public String description;
public String status;
public  int no_book;
@Lob
private byte[] Image_Data;
private String Image_Name;
private String Image_Type;
}
