package hse.hsebank.utils;

import lombok.Getter;

import java.util.UUID;

/**
 * Utility for generating and working with short UUIDs (8 characters)
 */
public class ShortUUID {

    /**
     * Generate a short UUID (8 characters)
     */
    public static String generate() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Generate a full UUID and return its short version
     */
    public static String generateShortFromUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Convert existing UUID to short version
     */
    public static String toShort(UUID uuid) {
        return uuid.toString().substring(0, 8);
    }

    /**
     * Validate if string is a valid short ID (8 hex characters)
     */
    public static boolean isValidShortId(String id) {
        return id != null && id.length() == 8 && id.matches("[a-fA-F0-9]{8}");
    }

    /**
     * Generate both full UUID and short ID
     */
    public static UUIDWithShort generateFullAndShort() {
        UUID fullUUID = UUID.randomUUID();
        String shortId = toShort(fullUUID);
        return new UUIDWithShort(fullUUID, shortId);
    }

    /**
     * Container for full UUID and short ID
     */
    @Getter
    public static class UUIDWithShort {
        private final UUID fullUUID;
        private final String shortId;

        public UUIDWithShort(UUID fullUUID, String shortId) {
            this.fullUUID = fullUUID;
            this.shortId = shortId;
        }

    }
}