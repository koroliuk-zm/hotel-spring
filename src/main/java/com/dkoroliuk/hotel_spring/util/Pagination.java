package com.dkoroliuk.hotel_spring.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

/**
 * Utility class that helps with pagination
 */
public class Pagination {

    /**
     * Secure constructor, to prevent instantiating this class
     */
    private Pagination() {}

    /**
     * Inner class that holds pagination parameters
     */
    @Getter
    @Setter
    public static class RoomPaginationParam {
        private String sortBy;
        private String sortOrder;
        private int perdayCost;
        private int seatsAmount;
        private String roomType;
        private String roomStatus;

        public RoomPaginationParam() {
            sortBy = "perdayCost";
            sortOrder = "asc";
            perdayCost = 0;
            seatsAmount = 0;
            roomType = "";
            roomStatus = ""; 
        }

        /**
         * Method to build pagination navigation menu
         * @param currentPage current page
         * @param totalPages total amount of pages
         * @return String that represent pagination navigation menu
         */
        public String buildPaginationNavigation(int currentPage, int totalPages) {
            if (totalPages == 1) {
                return "";
            }
            StringBuilder requestParams = new StringBuilder();
            requestParams
                    .append("/?sortBy=")
                    .append(sortBy)
                    .append("&sortOrder=")
                    .append(sortOrder)
                    .append("&perdayCost=")
                    .append(perdayCost)
                    .append("&seatsAmount=")
                    .append(seatsAmount)
                    .append("&roomType=")
                    .append(roomType)
                    .append("&roomStatus=")
                    .append(roomStatus)
                    .append("&page=");
            StringBuilder paginationNavigation = new StringBuilder();
            paginationNavigation.append("<nav><ul class=\"pagination pagination-lg\">");
            for (int i = 0; i < totalPages; i++) {
                if (i == currentPage - 1) {
                    paginationNavigation
                            .append("<li class=\"page-item active\"><span class=\"page-link\">")
                            .append(i+1)
                            .append("</span></li>");
                } else {
                    paginationNavigation
                            .append("<li class=\"page-item\"><a class=\"page-link\" href=\"")
                            .append(requestParams)
                            .append(i+1)
                            .append("\">")
                            .append(i+1)
                            .append("</a></li>");
                }
            }
            paginationNavigation
                    .append("</ul></nav>");
            return paginationNavigation.toString();
        }
    }

    /**
     * Method to build {@link List} of page numbers for pagination
     * @param totalPages total amount of pages
     * @return {@link List} of {@link Integer}
     */
    public static List<Integer> buildPageNumbers(int totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }

}
