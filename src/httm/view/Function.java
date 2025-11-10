package httm.view;

import java.io.IOException;
import java.util.*;
import java.math.*;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.net.http.HttpRequest.BodyPublishers;
import com.google.gson.reflect.TypeToken;
import httm.model.OrderDetail;
import httm.model.Product;
import httm.model.User;
import java.lang.reflect.Type;

public class Function {

    private class Interaction {

        @SerializedName("product_id")
        private String productId;

        @SerializedName("action")
        private String action;

        @SerializedName("brand")
        private String brand;

        @SerializedName("category_code")
        private String categoryCode;

        public Interaction(String productId, String action, String brand, String categoryCode) {
            this.productId = productId;
            this.action = action;
            this.brand = brand;
            this.categoryCode = categoryCode;
        }
    }

    private class RecommendBodyRequest {

        @SerializedName("user_id")
        private String userId;

        @SerializedName("top_k")
        private int topK;

        @SerializedName("interaction_history")
        private List<Interaction> interactionHistory;

        public RecommendBodyRequest(String userId, int topK, List<Interaction> interactionHistory) {
            this.userId = userId;
            this.topK = topK;
            this.interactionHistory = interactionHistory;
        }
    }

    private class Log {

        @SerializedName("user_id")
        private String userId;

        @SerializedName("product_id")
        private String productId;

        @SerializedName("action")
        private String action;

        @SerializedName("category_code")
        private String categoryCode;

        @SerializedName("brand")
        private String brand;

        public Log(String userId, String productId, String action, String categoryCode, String brand) {
            this.userId = userId;
            this.productId = productId;
            this.action = action;
            this.categoryCode = categoryCode;
            this.brand = brand;
        }
    }

    public String formatPrice(float price) {
        Locale localeVN = Locale.of("vi", "VN");
        NumberFormat formatter;
        formatter = NumberFormat.getInstance(localeVN);
        String formattedMoney = formatter.format(price) + " $";
        return formattedMoney;
    }

    public String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss 'ngày' dd/MM/yyyy");
        String formattedTime = time.format(formatter);
        return formattedTime;
    }

    public String getFromAPI(String apiUrl) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return response.body();
            } else {
                System.out.println("Mã lỗi: " + response.statusCode());
                System.out.println("Nội dung lỗi: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Lỗi khi gửi yêu cầu GET" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void sendLog(int userId, Product product, String actionType) {
        String apiUrl = "http://26.155.72.159:5000/api/samples";

//        Log requestBody = new Log(String.valueOf(userId), String.valueOf(product.getId()), actionType, product.getCategorycode(), product.getBrand());
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        requestBody.put("product_id", product.getId());
        requestBody.put("action", actionType);
        requestBody.put("category_code", product.getCategorycode());
        requestBody.put("brand", product.getBrand());

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(2))
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(2))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("GỬI LOG CHO SERVER ML: " + jsonPayload);
            } else {
                System.out.println("Gửi log thất bại (Mã lỗi server: " + response.statusCode() + ")");
                System.out.println("Nội dung lỗi: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Gửi log thất bại (Lỗi không xác định): " + e.getMessage());
        }

    }

    public String getRecommend(String apiUrl, User user, List<OrderDetail> interactionHistory) {
        List<Map<String, Object>> historyList = new ArrayList<>();
        for (OrderDetail item : interactionHistory) {
            Map<String, Object> interaction = new HashMap<>();

            Product product = item.getProduct();

            interaction.put("product_id", String.valueOf(product.getId()));
            interaction.put("action", "purchase");
            interaction.put("brand", product.getBrand());
            interaction.put("category_code", product.getCategorycode());

            historyList.add(interaction);
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", String.valueOf(user.getId()));
        requestBody.put("top_k", 15);
        requestBody.put("interaction_history", historyList);

        Gson gson = new Gson();
        String jsonPayload = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(2))
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(2))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("GỬI POST LỊCH SỬ TƯƠNG TÁC THÀNH CÔNG.");
                return response.body();
            } else {
                System.out.println("Gửi POST lịch sử thất bại (Mã lỗi server: " + response.statusCode() + ")");
                System.out.println("Nội dung lỗi: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Lỗi khi gửi yêu cầu POST lịch sử: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String getRcm(String apiUrl, User user, List<OrderDetail> interactionHistory) {
        List<Interaction> interactions = new ArrayList<>();
        for (OrderDetail inHis : interactionHistory) {
            Interaction interaction = new Interaction(String.valueOf(inHis.getProduct().getId()),
                    "purchase",
                    inHis.getProduct().getBrand(),
                    inHis.getProduct().getCategorycode());

            interactions.add(interaction);
        }

        RecommendBodyRequest rcmBodyRequest = new RecommendBodyRequest(String.valueOf(user.getId()), 15, interactions);
        Gson gson = new Gson();
        String jsonBodyRequest = gson.toJson(rcmBodyRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(2))
                .POST(BodyPublishers.ofString(jsonBodyRequest))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(2))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("GỬI POST LỊCH SỬ TƯƠNG TÁC THÀNH CÔNG.");
                return response.body();
            } else {
                System.out.println("Gửi POST lịch sử thất bại (Mã lỗi server: " + response.statusCode() + ")");
                System.out.println("Nội dung lỗi: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Lỗi khi gửi yêu cầu POST lịch sử: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> convertJsonToIdList(String json) {
        try {
            Type listType = new TypeToken<List<Integer>>() {
            }.getType();
            Gson gson = new Gson();
            List<Integer> ans = gson.fromJson(json, listType);
            return ans;
        } catch (Exception e) {
            System.out.println("Lỗi không thể convert JSON sang List<Integer>: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
//    public List<Integer> convertJsonToIdList1(String json) {
//        List<Integer> result = new ArrayList<>();
//        if (json == null || json.isEmpty()) {
//            return result;
//        }
//        Gson gson = new Gson();
//        try {
//            JsonDataWrapper wrapper = gson.fromJson(json, JsonDataWrapper.class);
//
//            if (wrapper != null && wrapper.data != null) {
//                for (String idStr : wrapper.data) {
//                    try {
//                        result.add(Integer.parseInt(idStr.trim()));
//                    } catch (NumberFormatException e) {
//                        System.out.println("Bỏ qua ID không hợp lệ: " + idStr);
//                    }
//                }
//                return result;
//            }
//        } catch (Exception e) {
//            try {
//                Type listType = new TypeToken<List<String>>() {
//                }.getType();
//                List<String> stringList = gson.fromJson(json, listType);
//
//                for (String idStr : stringList) {
//                    try {
//                        result.add(Integer.parseInt(idStr.trim()));
//                    } catch (NumberFormatException e1) {
//                        System.out.println("Bỏ qua ID không hợp lệ: " + idStr);
//                    }
//                }
//                return result;
//
//            } catch (Exception e2) {
//                System.out.println("Lỗi không thể convert JSON sang List<Integer>: " + e2.getMessage());
//                e2.printStackTrace(); 
//            }
//        }
//
//        return result;
//    }
//}
