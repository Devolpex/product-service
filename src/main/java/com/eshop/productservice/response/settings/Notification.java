package com.eshop.productservice.response.settings;

import java.util.ArrayList;
import java.util.List;

import com.eshop.productservice.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private String message;
    private String redirectTo;
    private NotificationStatus status;
    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();
    private List<String> infos = new ArrayList<>();
    private List<String> success = new ArrayList<>();

    
}
