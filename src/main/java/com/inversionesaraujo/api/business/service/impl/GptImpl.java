package com.inversionesaraujo.api.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inversionesaraujo.api.business.service.IGpt;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatCompletion.Choice;

@Service
public class GptImpl implements IGpt {
    @Autowired
    private OpenAIClient client;
    
    @Override
    public String ask(String question) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
            .model("gpt-4.1-mini")
            .addUserMessage(question)
            .build();

        ChatCompletion completion = client.chat().completions().create(params);

        Choice choice = completion.choices().get(0);
        System.out.println(choice);
        String message = choice._message().asObject().get().get("content").toString();

        return message;
    }
}
