package com.week2.chatgptapi.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import com.week2.chatgptapi.model.Conversation;
import com.week2.chatgptapi.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConversationController {

    @Autowired
    private ConversationRepository conversationRepository;

    @PostMapping("/prompts")
    public String getChatGptResponse(@RequestParam("question") String question) {
        // @RequestParam("token") String token, 
        String token = "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";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";
        String prompt = question;
        String model = "davinci-codex";
        String maxTokens = "150";
        String n = "1";
        String stop = "\"\n\"";

        String data = "{"
                + "\"prompt\": \"" + prompt + "\","
                + "\"model\": \"" + model + "\","
                + "\"max_tokens\": " + maxTokens + ","
                + "\"n\": " + n + ","
                + "\"stop\": [" + stop + "]"
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String chatGptResponse = response.getBody();
        String chatresponse = extractTextFromJson(chatGptResponse);

        Conversation conversation = new Conversation(question, chatresponse);
        conversationRepository.save(conversation);

        return chatresponse;

    }

    private String extractTextFromJson(String chatresponse) {
        System.out.println(chatresponse);
        JSONObject jsonObject = new JSONObject(chatresponse);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        return choice.getString("text");
    }

    @GetMapping("/conversations")
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
}
