package code;
public class State {
    private int initialProsperity;
    private int initialFood;
    private int initialMaterials;
    private int initialEnergy;

    private int unitPriceFood;
    private int unitPriceMaterials;
    private int unitPriceEnergy;

    private int amountRequestFood;
    private int delayRequestFood;

    private int amountRequestMaterials;
    private int delayRequestMaterials;

    private int amountRequestEnergy;
    private int delayRequestEnergy;

    private int priceBUILD1;
    private int foodUseBUILD1;
    private int materialsUseBUILD1;
    private int energyUseBUILD1;
    private int prosperityBUILD1;

    private int priceBUILD2;
    private int foodUseBUILD2;
    private int materialsUseBUILD2;
    private int energyUseBUILD2;
    private int prosperityBUILD2;

    private int budget;
    private int moneySpent;

    private int CurrentDelay = 0;
   
    private resource onTheWay = resource.None;




    public State(String encodedState) {
        String[] sections = encodedState.split(";");

        // Decode the first section for initial values
        String[] initialValues = sections[0].split(",");
        this.initialProsperity = Integer.parseInt(initialValues[0]);

        // Decode the second section for initial resource levels
        String[] initialResourceLevels = sections[1].split(",");
        this.initialFood = Integer.parseInt(initialResourceLevels[0]);
        this.initialMaterials = Integer.parseInt(initialResourceLevels[1]);
        this.initialEnergy = Integer.parseInt(initialResourceLevels[2]);

        // Decode the third section for unit prices
        String[] unitPrices = sections[2].split(",");
        this.unitPriceFood = Integer.parseInt(unitPrices[0]);
        this.unitPriceMaterials = Integer.parseInt(unitPrices[1]);
        this.unitPriceEnergy = Integer.parseInt(unitPrices[2]);

        // Decode the fourth section for request parameters
        String[] requestParamsFood = sections[3].split(",");
        this.amountRequestFood = Integer.parseInt(requestParamsFood[0]);
        this.delayRequestFood = Integer.parseInt(requestParamsFood[1]);

        String[] requestParamsMaterials = sections[4].split(",");
        this.amountRequestMaterials = Integer.parseInt(requestParamsMaterials[0]);
        this.delayRequestMaterials = Integer.parseInt(requestParamsMaterials[1]);

        String[] requestParamsEnergy = sections[5].split(",");
        this.amountRequestEnergy = Integer.parseInt(requestParamsEnergy[0]);
        this.delayRequestEnergy = Integer.parseInt(requestParamsEnergy[1]);

        // Decode the fifth section for BUILD1 parameters
        String[] build1Params = sections[6].split(",");
        this.priceBUILD1 = Integer.parseInt(build1Params[0]);
        this.foodUseBUILD1 = Integer.parseInt(build1Params[1]);
        this.materialsUseBUILD1 = Integer.parseInt(build1Params[2]);
        this.energyUseBUILD1 = Integer.parseInt(build1Params[3]);
        this.prosperityBUILD1 = Integer.parseInt(build1Params[4]);

        // Decode the sixth section for BUILD2 parameters
        String[] build2Params = sections[7].split(",");
        this.priceBUILD2 = Integer.parseInt(build2Params[0]);
        this.foodUseBUILD2 = Integer.parseInt(build2Params[1]);
        this.materialsUseBUILD2 = Integer.parseInt(build2Params[2]);
        this.energyUseBUILD2 = Integer.parseInt(build2Params[3]);
        this.prosperityBUILD2 = Integer.parseInt(build2Params[4]);

        this.moneySpent = 0;
        this.budget = 100000;
    }

    public int getInitialProsperity() {
        return initialProsperity;
    }

    public int getInitialFood() {
        return initialFood;
    }

    public int getInitialMaterials() {
        return initialMaterials;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public int getUnitPriceFood() {
        return unitPriceFood;
    }

    public int getUnitPriceMaterials() {
        return unitPriceMaterials;
    }

    public int getUnitPriceEnergy() {
        return unitPriceEnergy;
    }

    public int getAmountRequestFood() {
        return amountRequestFood;
    }

    public int getDelayRequestFood() {
        return delayRequestFood;
    }

    public int getAmountRequestMaterials() {
        return amountRequestMaterials;
    }

    public int getDelayRequestMaterials() {
        return delayRequestMaterials;
    }

    public int getAmountRequestEnergy() {
        return amountRequestEnergy;
    }

    public int getDelayRequestEnergy() {
        return delayRequestEnergy;
    }

    public int getPriceBUILD1() {
        return priceBUILD1;
    }

    public int getFoodUseBUILD1() {
        return foodUseBUILD1;
    }

    public int getMaterialsUseBUILD1() {
        return materialsUseBUILD1;
    }

    public int getEnergyUseBUILD1() {
        return energyUseBUILD1;
    }

    public int getProsperityBUILD1() {
        return prosperityBUILD1;
    }

    public int getPriceBUILD2() {
        return priceBUILD2;
    }

    public int getFoodUseBUILD2() {
        return foodUseBUILD2;
    }

    public int getMaterialsUseBUILD2() {
        return materialsUseBUILD2;
    }

    public int getEnergyUseBUILD2() {
        return energyUseBUILD2;
    }

    public int getProsperityBUILD2() {
        return prosperityBUILD2;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getBudget() {
        return budget;
    }

     public int getCurrentDelay() {
        return CurrentDelay;
    }
    public resource getonTheWay() {
        return onTheWay;
    }
    // Setters
    public void setonTheWay(resource onTheWay) {
    	
        this.onTheWay = onTheWay;
    }
    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }
    
    public void setCurrentDelay(int CurrentDelay) {
        this.CurrentDelay = CurrentDelay;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setInitialProsperity(int initialProsperity) {
        this.initialProsperity = initialProsperity;
    }

    public void setInitialFood(int initialFood) {
        this.initialFood = initialFood;
    }

    public void setInitialMaterials(int initialMaterials) {
        this.initialMaterials = initialMaterials;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public void setUnitPriceFood(int unitPriceFood) {
        this.unitPriceFood = unitPriceFood;
    }

    public void setUnitPriceMaterials(int unitPriceMaterials) {
        this.unitPriceMaterials = unitPriceMaterials;
    }

    public void setUnitPriceEnergy(int unitPriceEnergy) {
        this.unitPriceEnergy = unitPriceEnergy;
    }

    public void setAmountRequestFood(int amountRequestFood) {
        this.amountRequestFood = amountRequestFood;
    }

    public void setDelayRequestFood(int delayRequestFood) {
        this.delayRequestFood = delayRequestFood;
    }

    public void setAmountRequestMaterials(int amountRequestMaterials) {
        this.amountRequestMaterials = amountRequestMaterials;
    }

    public void setDelayRequestMaterials(int delayRequestMaterials) {
        this.delayRequestMaterials = delayRequestMaterials;
    }

    public void setAmountRequestEnergy(int amountRequestEnergy) {
        this.amountRequestEnergy = amountRequestEnergy;
    }

    public void setDelayRequestEnergy(int delayRequestEnergy) {
        this.delayRequestEnergy = delayRequestEnergy;
    }

    public void setPriceBUILD1(int priceBUILD1) {
        this.priceBUILD1 = priceBUILD1;
    }

    public void setFoodUseBUILD1(int foodUseBUILD1) {
        this.foodUseBUILD1 = foodUseBUILD1;
    }

    public void setMaterialsUseBUILD1(int materialsUseBUILD1) {
        this.materialsUseBUILD1 = materialsUseBUILD1;
    }

    public void setEnergyUseBUILD1(int energyUseBUILD1) {
        this.energyUseBUILD1 = energyUseBUILD1;
    }

    public void setProsperityBUILD1(int prosperityBUILD1) {
        this.prosperityBUILD1 = prosperityBUILD1;
    }

    public void setPriceBUILD2(int priceBUILD2) {
        this.priceBUILD2 = priceBUILD2;
    }

    public void setFoodUseBUILD2(int foodUseBUILD2) {
        this.foodUseBUILD2 = foodUseBUILD2;
    }

    public void setMaterialsUseBUILD2(int materialsUseBUILD2) {
        this.materialsUseBUILD2 = materialsUseBUILD2;
    }

    public void setEnergyUseBUILD2(int energyUseBUILD2) {
        this.energyUseBUILD2 = energyUseBUILD2;
    }

    public void setProsperityBUILD2(int prosperityBUILD2) {
        this.prosperityBUILD2 = prosperityBUILD2;
    }

    public static void main(String[] args) {
        String encodedState = "50;" +
                "22,22,22;" +
                "50,60,70;" +
                "30,2;19,1;15,1;" +
                "300,5,7,3,20;" +
                "500,8,6,3,40;";

        State state = new State(encodedState);

        // Print the values for testing
        System.out.println("Initial Prosperity: " + state.getInitialProsperity());
        System.out.println("Initial Food: " + state.getInitialFood());
        System.out.println("Initial Materials: " + state.getInitialMaterials());
        System.out.println("Initial Energy: " + state.getInitialEnergy());
        System.out.println("Unit Price Food: " + state.getUnitPriceFood());
        System.out.println("Unit Price Materials: " + state.getUnitPriceMaterials());
        System.out.println("Unit Price Energy: " + state.getUnitPriceEnergy());
        System.out.println("Amount Request Food: " + state.getAmountRequestFood());
        System.out.println("Delay Request Food: " + state.getDelayRequestFood());
        System.out.println("Amount Request Materials: " + state.getAmountRequestMaterials());
        System.out.println("Delay Request Materials: " + state.getDelayRequestMaterials());
        System.out.println("Amount Request Energy: " + state.getAmountRequestEnergy());
        System.out.println("Delay Request Energy: " + state.getDelayRequestEnergy());
        System.out.println("Price BUILD1: " + state.getPriceBUILD1());
        System.out.println("Food Use BUILD1: " + state.getFoodUseBUILD1());
        System.out.println("Materials Use BUILD1: " + state.getMaterialsUseBUILD1());
        System.out.println("Energy Use BUILD1: " + state.getEnergyUseBUILD1());
        System.out.println("Prosperity BUILD1: " + state.getProsperityBUILD1());
        System.out.println("Price BUILD2: " + state.getPriceBUILD2());
        System.out.println("Food Use BUILD2: " + state.getFoodUseBUILD2());
        System.out.println("Materials Use BUILD2: " + state.getMaterialsUseBUILD2());
        System.out.println("Energy Use BUILD2: " + state.getEnergyUseBUILD2());
        System.out.println("Prosperity BUILD2: " + state.getProsperityBUILD2());
    }
}