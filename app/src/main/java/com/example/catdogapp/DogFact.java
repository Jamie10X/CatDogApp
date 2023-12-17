package com.example.catdogapp;



import java.util.List;
    class DogFactResponse {
    private List<DogFact> data;

    public List<DogFact> getData() {
        return data;
    }

    public static class DogFact {
        private String id;
        private String type;
        private Attributes attributes;

        public String getFact() {
            return attributes != null ? attributes.body : null;
        }

        public class Attributes {
            private String body;
        }
    }
}

