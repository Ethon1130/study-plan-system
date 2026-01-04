package com.learning.utils;

import com.learning.model.LearningPlan;

import java.io.*;
import java.util.List;

public class DataPersistence {

    private static final String DATA_FILE = "learning_data.ser";

    public static void saveData(List<LearningPlan> plans, int nextPlanId, int nextRecordId) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            Data data = new Data(plans, nextPlanId, nextRecordId);
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Data loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Data) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = 1L;
        private List<LearningPlan> plans;
        private int nextPlanId;
        private int nextRecordId;

        public Data(List<LearningPlan> plans, int nextPlanId, int nextRecordId) {
            this.plans = plans;
            this.nextPlanId = nextPlanId;
            this.nextRecordId = nextRecordId;
        }

        public List<LearningPlan> getPlans() {
            return plans;
        }

        public int getNextPlanId() {
            return nextPlanId;
        }

        public int getNextRecordId() {
            return nextRecordId;
        }
    }
}
