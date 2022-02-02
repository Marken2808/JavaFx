package com.example.apispring.services;

import com.example.apispring.models.Wrapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Connection {

    private String token = "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Uy5KbMBD8lRTnnS2MsXnccssP5AOG0chWGSRKErvZSuXfI5AwxnHl5u6eR89o8O9MOZe1GY4KBA_m3Xm0vdKXDvXtncyQvWVu6kJELmtZ5XkDsqkKKCvqAHORQ3WkUyFEkVMpQzD_GrP2cC6PTXFqDvVbptBHoj42p5lAIjNp_8P0gu1PJULtA3MlKe-g4uIEZSMZmlpKKLpT3RwaWdQsQm1vbqxjRk4kzkdmyMu8gpJQQHMsGyiPshYl8VnkFDLCWN-J2LmYRRgqcheyJOZQdnUJTSkbqMqzPJXnc3DczAOTGXleSnQK1BvHorWM4tvKXRf7oHHgl4L_Gp8EJVh7JRXbPd8r53dMAkLYYLxlofwdRMV7pOvA98gNf1rl-RtO_mqscuEZQWmhPpSYsI_BHfaoKVkjtALIaG9NHxvNTNKMlsoO6JXRYCTISQt3l9y9-wpia5qcN8M6Ig-oUuGegxF9aXEc-687WqIG1AI9t4J7DiVWmDR7Yz8PMlqWbDl4d_-Too2ojT0Shw14vthljsfEf8WUypauuE43sMfgBlsKcFETXoYa8Yt5lSJIQ0SwBYEa8JJmitr2E7xF7ZA2h4GGbupv7fqSvFFbt4i3hhGvBeZ7CLc1KL_V7A2FO3iosBBg5oN4ZlOWNVL1q_04z45aoiwTq9HvgNtLcbkOP8KDObiYzceOS9PsuKXOIxMXFp78VYlNfFFrE2NRurKYehaQFpZo9j4MOI0Jjrh-MOHfcTlcMFY8tN-za989-yIfzKe-856XFyP38UyNQiZq6hzZsM35UNYuj9wS9XhNy4M9n1f25y9G704X9gUAAA.aHOfjjNex6PuMOSv6wXFo210hq6a6zioERa0wUybPoXbc9a_sG1duLNCz0mUxct8BoFYU4qESe_uNSDJYbfBkaedY3JFusLhCqQPNSkw8pVwggxZsPBnswOMW0Uw58yWaoLa13bFSSv_oXl8CdxIaVx9ynoYrM3LZZ7SXEkQOwrtjgVZLX928SpF8aPbkfMjGCmvm_Uw0FLwNpI2g2WboAiFo8IpWgkEfa2zHS2VAru3IwiSv19s0qzV5fPq7uJTQbbcLRJrcB2bhjD5gsa5jNJt4B0-JmxGiMhtRjmFy4c59MSks_YYhVcGs422ZJrnsgVeOUBmDR54GI0rwcMiGW2L7do1-4j6jBsUhWoqj0IxiteM6wNqvjQhBdNSMeuRlbmbA-UE0Yw7NPCvj0pyTsJj7fdRjTE9hKKvZPcOsUQ9O99OXgq6RXTR-KAklYGLWYEZPUF9fm9SOgDqX9U2Z-Ur29OHzxZn5fXk0jTE2Jfb-NY5YVwEJQd6grnTDw1nG5crBFLePnnM76G7yb58V-Stq6nRoQBuef2SkVauXpUrfs3_ayIrnZxjkEkuvCuey_VM3A1gp6xfwO-FUSp1uyqCTNmCtAYW9Fdgm6Nn8YXWhcoAf8HuOjEaUHdBDTd4EEuGtifkvU4YVU7P0yMRWcOKZcv8ArjnfQZna9lSboU";
    public Wrapper builder(String api, HttpMethod method) {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("User-Agent","TUAN CONG NGUYEN");
        headers.setBearerAuth(token);

        ResponseEntity<Wrapper> response = restTemplate.exchange(api, method, new HttpEntity(headers), Wrapper.class);
        return  response.getBody();
    }

}
