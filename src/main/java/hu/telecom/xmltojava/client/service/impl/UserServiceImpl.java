package hu.telecom.xmltojava.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.telecom.xmltojava.client.model.User;
import hu.telecom.xmltojava.client.service.UserService;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

import static hu.telecom.xmltojava.client.constants.Constants.GET_URL;
import static hu.telecom.xmltojava.client.constants.Constants.POST_URL;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void listUsers() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(GET_URL);
            processUserListResponse(client, httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processUserListResponse(CloseableHttpClient client, HttpGet httpGet) throws IOException {
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getCode() == 200) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                logger.info("Response body: {}", responseBody);

                User[] users = objectMapper.readValue(responseBody, User[].class);
                for (User user : users) {
                    System.out.println(user);
                }
            } else {
                System.out.println("Failed to retrieve users: " + response.getCode());
            }
        } catch (HttpResponseException e) {
            System.out.println("Failed to retrieve users: " + e.getMessage());
        }
    }

    @Override
    public void addUser(Scanner scanner) {
        User user = createUserByEnteredData(scanner);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = createHttpPost(user);
            processAddUserResponse(client, post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User createUserByEnteredData(Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter address:");
        String address = scanner.nextLine();

        User.Roles roles = new User.Roles();
        System.out.println("Enter roles (comma-separated):");
        String[] roleNames = scanner.nextLine().split(",");
        for (String roleName : roleNames) {
            roles.getRole().add(roleName.trim().toUpperCase());
        }

        return new User(null, name, username, address, roles);
    }


    private HttpPost createHttpPost(User user) throws IOException {
        HttpPost post = new HttpPost(POST_URL);
        String json = objectMapper.writeValueAsString(user);
        post.setEntity(new StringEntity(json));
        post.setHeader("Content-Type", "application/json; charset=UTF-8");

        return post;
    }

    private void processAddUserResponse(CloseableHttpClient client, HttpPost post) throws IOException {
        try (CloseableHttpResponse response = client.execute(post)) {
            if (response.getCode() == 200) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user: " + response.getCode());
            }
        } catch (HttpResponseException e) {
            System.out.println("Failed to add user: " + e.getMessage());
        }
    }
}
