package com.fitness.aiservice.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class GeminiService {

    @Value("${gemini.project-id}")
    private String projectId;

    @Value("${gemini.location}")
    private String location;

    @Value("${gemini.model-name}")
    private String modelName;

    public String getAnswer(String question) {
        try (VertexAI vertexAI = new VertexAI(projectId, location)) {

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);

            GenerateContentResponse response = model.generateContent(question);

            String textResponse = ResponseHandler.getText(response);
            log.info("AI response received successfully.");
            return textResponse;

        } catch (IOException e) {
            log.error("Error communicating with Vertex AI", e);
            return "Error: Could not get a response from the AI service.";
        }
    }
}