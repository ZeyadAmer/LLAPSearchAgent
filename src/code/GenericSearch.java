package code;

import java.util.*;

public class GenericSearch {
	int nodesExpanded;
	int monetaryCost;
	String plan;
	String Solution;
	

	public static void sortQueue(Queue<Node> queue) {
		List<Node> list = new LinkedList<>(queue);
		Collections.sort(list, Comparator.comparingDouble(Node::getHeuristic).thenComparingInt(Node::getDepth));
		queue.clear();
		queue.addAll(list);
	}
	public static void sortQueue2(Queue<Node> queue) {
		List<Node> list = new LinkedList<>(queue);
		Collections.sort(list, Comparator.comparingDouble(Node::getHeuristic2));
		queue.clear();
		queue.addAll(list);
	}
	public static void sortQueueAS(Queue<Node> queue) {
		List<Node> list = new LinkedList<>(queue);
		Collections.sort(list, Comparator.comparingDouble(Node::getAS).thenComparingInt(Node::getDepth));
		queue.clear();
		queue.addAll(list);
	}
	public static void sortQueueAS2(Queue<Node> queue) {
		List<Node> list = new LinkedList<>(queue);
		Collections.sort(list, Comparator.comparingDouble(Node::getAS2));
		queue.clear();
		queue.addAll(list);
	}

	// I should make 5 nodes men init state
	public void operation(Node node) {
		resource onTheWay;
		// todo the action then go to next node
		switch (node.operator) {
			case root:
				// wala haga
				return;
			case RequestFood:
				// todo nezbot el wait + nezbot el money spent + nezbot el resources + onTheWay
				node.state.setCurrentDelay(node.state.getDelayRequestFood());
				node.state.setMoneySpent(node.state.getMoneySpent()+node.state.getUnitPriceEnergy() + node.state.getUnitPriceFood()
						+ node.state.getUnitPriceMaterials());
				node.state.setInitialEnergy(node.state.getInitialEnergy() - 1);
				node.state.setInitialFood(node.state.getInitialFood() - 1);
				node.state.setInitialMaterials(node.state.getInitialMaterials() - 1);
				onTheWay = resource.Food;
				node.state.setonTheWay(onTheWay);
				break;

			case RequestMaterials:

				node.state.setCurrentDelay(node.state.getDelayRequestMaterials());
				node.state.setMoneySpent(node.state.getMoneySpent() + node.state.getUnitPriceEnergy()
						+ node.state.getUnitPriceFood() + node.state.getUnitPriceMaterials());
				node.state.setInitialEnergy(node.state.getInitialEnergy() - 1);
				node.state.setInitialFood(node.state.getInitialFood() - 1);
				node.state.setInitialMaterials(node.state.getInitialMaterials() - 1);
				onTheWay = resource.Materials;
				node.state.setonTheWay(onTheWay);
				break;

			case RequestEnergy:

				node.state.setCurrentDelay(node.state.getDelayRequestEnergy());
				node.state.setMoneySpent(node.state.getMoneySpent()+node.state.getUnitPriceEnergy() + node.state.getUnitPriceFood()
						+ node.state.getUnitPriceMaterials());
				node.state.setInitialEnergy(node.state.getInitialEnergy() - 1);
				node.state.setInitialFood(node.state.getInitialFood() - 1);
				node.state.setInitialMaterials(node.state.getInitialMaterials() - 1);
				onTheWay = resource.Energy;
				node.state.setonTheWay(onTheWay);
				break;

			case BUILD1:
				// change prosperity + change resources + money spent + reduce the currentDelay
				node.state.setInitialProsperity(node.state.getProsperityBUILD1() + node.state.getInitialProsperity());
				node.state.setInitialEnergy(node.state.getInitialEnergy() - node.state.getEnergyUseBUILD1());
				node.state.setInitialFood(node.state.getInitialFood() - node.state.getFoodUseBUILD1());
				node.state.setInitialMaterials(node.state.getInitialMaterials() - node.state.getMaterialsUseBUILD1());
				node.state.setMoneySpent(
						node.state.getMoneySpent() + (node.state.getUnitPriceEnergy() * node.state.getEnergyUseBUILD1())
								+ (node.state.getFoodUseBUILD1() * node.state.getUnitPriceFood() )+
								(node.state.getUnitPriceMaterials() * node.state.getMaterialsUseBUILD1())+ node.state.getPriceBUILD1());
				if (node.state.getCurrentDelay() > 0) {
					node.state.setCurrentDelay(node.state.getCurrentDelay() - 1);
				}
				break;

			case BUILD2:
				node.state.setInitialProsperity(node.state.getProsperityBUILD2() + node.state.getInitialProsperity());
				node.state.setInitialEnergy(node.state.getInitialEnergy() - node.state.getEnergyUseBUILD2());
				node.state.setInitialFood(node.state.getInitialFood() - node.state.getFoodUseBUILD2());
				node.state.setInitialMaterials(node.state.getInitialMaterials() - node.state.getMaterialsUseBUILD2());
				node.state.setMoneySpent(
						node.state.getMoneySpent() + (node.state.getUnitPriceEnergy() * node.state.getEnergyUseBUILD2())
								+ (node.state.getFoodUseBUILD2() * node.state.getUnitPriceFood() )+
								(node.state.getUnitPriceMaterials() * node.state.getMaterialsUseBUILD2())+ node.state.getPriceBUILD2());
				if (node.state.getCurrentDelay() > 0) {
					node.state.setCurrentDelay(node.state.getCurrentDelay() - 1);
				}
				break;

			case WAIT:
				node.state.setMoneySpent(node.state.getMoneySpent() + node.state.getUnitPriceEnergy()
						+ node.state.getUnitPriceFood() + node.state.getUnitPriceMaterials());
				if (node.state.getCurrentDelay() > 0) {
					node.state.setCurrentDelay(node.state.getCurrentDelay() - 1);
				}
				node.state.setInitialEnergy(node.state.getInitialEnergy() - 1);
				node.state.setInitialFood(node.state.getInitialFood() - 1);
				node.state.setInitialMaterials(node.state.getInitialMaterials() - 1);
				break;

			default:
				break;
		}
	}

	public String BF(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {
			
			Node current = Tree.remove();
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}
			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs we han push bel 3aks 3asa da stack fa akhod el awel el
				// awell
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				
				
			} else {
				// hane3mel normal 5 we han push bel 3aks 3asa da stack fa akhod el awel el awel
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);;
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child5);
				
				
				
				
			}
			
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
			
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
			
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}

	}

	public String DF(Stack<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.pop();
		Tree.push(finalNode);
		while (!Tree.isEmpty()) {
			
			Node current = Tree.pop();
			
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
			
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}
			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs we han push bel 3aks 3asa da stack fa akhod el awel el
				// awellll
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.push(Child3);
				Tree.push(Child2);
				Tree.push(Child1);
			} else {
				// hane3mel normal 5 we han push bel 3aks 3asa da stack fa akhod el awel el awel
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestMaterials, current.depth + 1);
				Node Child3 = new Node(State3, current, ActionEnum.RequestEnergy, current.depth + 1);
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.push(Child1);
				Tree.push(Child2);
				Tree.push(Child3);
				Tree.push(Child4);
				Tree.push(Child5);
			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
			
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
			
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			
			return Solution;
		}
	}

	public String ID(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		int level = -1;
		Node finalNode = Tree.remove();
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {
			
			Node current = Tree.remove();
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			Tree.add(current);
			if (current.depth == 0) {
				level++;
			}
			if (level != current.depth) {
			}
			else{
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}
			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs we han push bel 3aks 3asa da stack fa akhod el awel el
				// awell
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				
				
			} else {
				// hane3mel normal 5 we han push bel 3aks 3asa da stack fa akhod el awel el awel
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);;
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child5);
				
				
				
				
			}
			
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
	}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
			
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
			
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	

	}

	public String UC(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {
			
			Node current = Tree.remove();
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}
			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				if (Child1.cost > Child2.cost) {
					Tree.add(Child2);
					Tree.add(Child1);
				} else {
					Tree.add(Child1);
					Tree.add(Child2);
				}

			} else {
				// hane3mel normal 5
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);;
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
			
				if (Child4.cost > Child5.cost) {
					Tree.add(Child5);
					Tree.add(Child4);
				} else {
					Tree.add(Child4);
					Tree.add(Child5);
				}
			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
		
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
			
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	}

	public String GR1(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		Queue<Node> builds = new LinkedList<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Node current;
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {


			
			if(!builds.isEmpty()){
			 current = builds.remove();
			}else{
			 current = Tree.remove();
			}
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
		
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}

			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				builds.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				builds.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				sortQueue(builds);

			} else {
				// hane3mel normal 5
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				builds.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				builds.add(Child5);
				sortQueue(builds);

			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
	
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
		
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	}

	public String GR2(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Node current;
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {


			
			 current = Tree.remove();
			
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
		
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}

			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				sortQueue2(Tree);

			} else {
				// hane3mel normal 5
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child5);
				sortQueue2(Tree);

			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
	
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
		
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	}

	public String AS1(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		Queue<Node> builds = new LinkedList<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Node current;
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {


			
			if(!builds.isEmpty()){
			 current = builds.remove();
			}else{
			 current = Tree.remove();
			}
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
		
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}

			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				builds.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				builds.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				sortQueueAS(builds);

			} else {
				// hane3mel normal 5
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				builds.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				builds.add(Child5);
				sortQueueAS(builds);

			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
	
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
		
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	}

	public String AS2(Queue<Node> Tree, boolean visualize) {
		HashSet<String> repeatList = new HashSet<>();
		String x = "lol";
		repeatList.add(x);
		nodesExpanded = 0;
		plan = "";
		monetaryCost = 0;
		Node finalNode = Tree.remove();
		Node current;
		Tree.add(finalNode);
		while (!Tree.isEmpty()) {


			
			 current = Tree.remove();
			
			if(visualize == true){
				System.out.println(current.operator.toString());
			}
			
		
			if (current.state.getCurrentDelay() == 1) {
				operation(current);
				switch (current.state.getonTheWay()) {
					case Food:
						current.state
								.setInitialFood(current.state.getInitialFood() + current.state.getAmountRequestFood());
						if (current.state.getInitialFood() > 50) {
							current.state.setInitialFood(50);
						}
						
						current.state.setonTheWay(resource.None);
						break;
					case Materials:
						current.state.setInitialMaterials(
								current.state.getInitialMaterials() + current.state.getAmountRequestMaterials());
						if (current.state.getInitialMaterials() > 50) {
							current.state.setInitialMaterials(50);
						}
						current.state.setonTheWay(resource.None);
						break;
					case Energy:
						current.state.setInitialEnergy(
								current.state.getInitialEnergy() + current.state.getAmountRequestEnergy());
						if (current.state.getInitialEnergy() > 50) {
							current.state.setInitialEnergy(50);
						}
						current.state.setonTheWay(resource.None);
						break;

				}
			} else {
				operation(current);
			}

			String avoidRepeat = String.valueOf(current.state.getInitialProsperity()) + " " + String.valueOf(current.state.getInitialEnergy()) + " " + String.valueOf(current.state.getInitialFood()) + " " +
			String.valueOf(current.state.getInitialMaterials()) + " " + String.valueOf(current.state.getCurrentDelay()) + " " + current.state.getonTheWay().toString() + " " + current.operator.toString();  
			current.setAvoidRepeat(avoidRepeat);
			
			
			
			if(isStringInList(repeatList, current.avoidRepeat)){
				// we kill this node we won't make children
				

			}
			else if (current.state.getInitialEnergy() <= 0 || current.state.getInitialFood() <= 0
					|| current.state.getInitialMaterials() <= 0 || current.state.getMoneySpent() >= 100000) {
				// we kill this node we won't make children

			}
			else if (current.state.getInitialProsperity() >= 100) {
				finalNode = current;
				break;

			} else if (current.state.getCurrentDelay() > 0) {
				// han make only 3 childs
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				Node Child1 = new Node(State1, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.WAIT, current.depth + 1);
				Tree.add(Child3);
				sortQueueAS2(Tree);

			} else {
				// hane3mel normal 5
				String newState = encodeState(current.state);
				State State1 = new State(newState);
				State State2 = new State(newState);
				State State3 = new State(newState);
				State State4 = new State(newState);
				State State5 = new State(newState);
				State1.setCurrentDelay(current.state.getCurrentDelay());
				State1.setonTheWay(current.state.getonTheWay());
				State1.setMoneySpent(current.state.getMoneySpent());
				State2.setMoneySpent(current.state.getMoneySpent());
				State2.setCurrentDelay(current.state.getCurrentDelay());
				State2.setonTheWay(current.state.getonTheWay());
				State3.setCurrentDelay(current.state.getCurrentDelay());
				State3.setonTheWay(current.state.getonTheWay());
				State3.setMoneySpent(current.state.getMoneySpent());
				State4.setMoneySpent(current.state.getMoneySpent());
				State4.setCurrentDelay(current.state.getCurrentDelay());
				State4.setonTheWay(current.state.getonTheWay());
				State5.setMoneySpent(current.state.getMoneySpent());
				State5.setCurrentDelay(current.state.getCurrentDelay());
				State5.setonTheWay(current.state.getonTheWay());
				Node Child1 = new Node(State1, current, ActionEnum.RequestFood, current.depth + 1);
				Tree.add(Child1);
				Node Child2 = new Node(State2, current, ActionEnum.RequestEnergy, current.depth + 1);
				Tree.add(Child2);
				Node Child3 = new Node(State3, current, ActionEnum.RequestMaterials, current.depth + 1);
				Tree.add(Child3);
				Node Child4 = new Node(State4, current, ActionEnum.BUILD1, current.depth + 1);
				Tree.add(Child4);
				Node Child5 = new Node(State5, current, ActionEnum.BUILD2, current.depth + 1);
				Tree.add(Child5);
				sortQueueAS2(Tree);

			}
			repeatList.add(current.avoidRepeat);
			nodesExpanded++;
			finalNode = current;
		}
		if (Tree.isEmpty()) {
			Solution = "NOSOLUTION";
			return Solution;
		} else {
	
			Stack<String> pathStack = new Stack<String>();
			monetaryCost = finalNode.state.getMoneySpent();
			while (finalNode.parentNode != null) {
				pathStack.push(finalNode.operator.toString());
				finalNode = finalNode.parentNode;

			}
			
			Solution = pathStack.pop().toString();
			Solution += ",";
		
			while (!pathStack.isEmpty()) {
				Solution += pathStack.pop();
				if(!pathStack.isEmpty()){
					Solution += ",";
				}
				
			}
			Solution += ";";
			Solution += monetaryCost;
			Solution += ";";
			Solution += nodesExpanded;
			return Solution;
		}
	}


	public static String encodeState(State state) {
        // Encode initial values
        String encodedState = state.getInitialProsperity() + ";";
    
        // Encode initial resource levels
        encodedState += state.getInitialFood() + "," + state.getInitialMaterials() + "," + state.getInitialEnergy() + ";";
    
        // Encode unit prices
        encodedState += state.getUnitPriceFood() + "," + state.getUnitPriceMaterials() + "," + state.getUnitPriceEnergy() + ";";
    
        // Encode request parameters
        encodedState += state.getAmountRequestFood() + "," + state.getDelayRequestFood() + ";";
        encodedState += state.getAmountRequestMaterials() + "," + state.getDelayRequestMaterials() + ";";
        encodedState += state.getAmountRequestEnergy() + "," + state.getDelayRequestEnergy() + ";";
    
        // Encode BUILD1 parameters
        encodedState += state.getPriceBUILD1() + "," + state.getFoodUseBUILD1() + "," + state.getMaterialsUseBUILD1() + "," +
                state.getEnergyUseBUILD1() + "," + state.getProsperityBUILD1() + ";";
    
        // Encode BUILD2 parameters
        encodedState += state.getPriceBUILD2() + "," + state.getFoodUseBUILD2() + "," + state.getMaterialsUseBUILD2() + "," +
                state.getEnergyUseBUILD2() + "," + state.getProsperityBUILD2();
    
        return encodedState;
    }


	
	public static boolean isStringInList(HashSet<String> list, String searchStr) {
		
			if (list.contains(searchStr)) {
				return true;
			}else{
				return false;
			}
		
		
		
	}


}
