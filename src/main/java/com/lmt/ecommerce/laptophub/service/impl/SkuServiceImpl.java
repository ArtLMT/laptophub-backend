package com.lmt.ecommerce.laptophub.service.impl;

import com.lmt.ecommerce.laptophub.service.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Implementation of SKU service for generating and validating Stock Keeping Unit codes.
 * Encapsulates all SKU-related string manipulation and generation logic.
 */
@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private static final int MAX_SKU_NAME_LENGTH = 15;
    private static final String SKU_DELIMITER = "-";
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
    private static final Pattern PRODUCT_NAME_SPECIAL_CHARS = Pattern.compile("[^a-zA-Z0-9 ]");

    @Override
    public String generateSmartSkuCode(String productName, Map<String, Object> variantAttributes) {
        StringBuilder skuBuilder = new StringBuilder();

        String sanitizedProductName = sanitizeProductName(productName);
        String truncatedNamePart = truncateNameIfTooLong(sanitizedProductName);
        skuBuilder.append(truncatedNamePart);

        appendAttributesIfPresent(skuBuilder, variantAttributes);

        return skuBuilder.toString();
    }

    @Override
    public String sanitizeSkuCode(String skuCode) {
        if (skuCode == null || skuCode.trim().isEmpty()) {
            return null;
        }
        return skuCode.trim();
    }

    @Override
    public String resolveSkuCode(String providedSkuCode, String productName, Map<String, Object> variantAttributes) {
        if (providedSkuCode != null && !providedSkuCode.trim().isEmpty()) {
            return providedSkuCode.trim();
        }
        return generateSmartSkuCode(productName, variantAttributes);
    }

    private String sanitizeProductName(String productName) {
        return productName
                .replaceAll(PRODUCT_NAME_SPECIAL_CHARS.pattern(), "")
                .toUpperCase()
                .replace(" ", SKU_DELIMITER);
    }

    private String truncateNameIfTooLong(String name) {
        if (name.length() > MAX_SKU_NAME_LENGTH) {
            return name.substring(0, MAX_SKU_NAME_LENGTH);
        }
        return name;
    }

    private void appendAttributesIfPresent(StringBuilder skuBuilder, Map<String, Object> variantAttributes) {
        if (variantAttributes == null) {
            return;
        }

        appendAttributeIfPresent(skuBuilder, variantAttributes, "ram");
        appendAttributeIfPresent(skuBuilder, variantAttributes, "storage");
        appendAttributeIfPresent(skuBuilder, variantAttributes, "storageCapacity");
        appendAttributeIfPresent(skuBuilder, variantAttributes, "color");
    }

    private void appendAttributeIfPresent(StringBuilder skuBuilder, Map<String, Object> variantAttributes, String attributeKey) {
        if (!variantAttributes.containsKey(attributeKey)) {
            return;
        }

        Object attributeValue = variantAttributes.get(attributeKey);
        if (attributeValue != null) {
            String sanitizedValue = attributeValue.toString()
                    .toUpperCase()
                    .replaceAll(ALPHANUMERIC_PATTERN.pattern(), "");
            skuBuilder.append(SKU_DELIMITER).append(sanitizedValue);
        }
    }
}

