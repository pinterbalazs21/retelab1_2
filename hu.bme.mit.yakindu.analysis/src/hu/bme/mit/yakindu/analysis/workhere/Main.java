package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.base.types.Event;
import org.yakindu.base.types.Property;
import org.yakindu.sct.model.sgraph.Scope;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		Integer stateID=0;
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		//2.feladat:
		/*
		TreeIterator<EObject> iterator = s.eAllContents();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
				if(content instanceof State) {
					State state = (State) content;
					//System.out.println(state.getName());
					for(Transition t : state.getOutgoingTransitions()) {
						System.out.println(state.getName() + " -> " + t.getTarget().getName());
					}
					if(state.getOutgoingTransitions().isEmpty()) {
						System.out.println(state.getName() + " is a trap state!");
					}
					if(state.getName().equals("")) {
						System.out.println("State with no name found, recommended name: State"+ stateID.toString());
						stateID++;
					}
				}
		}*/
		//4.feladat:
		EList<Scope> scopes = s.getScopes();
		System.out.println("public static void main(String[] args) throws IOException {");
		System.out.println("	ExampleStatemachine s = new ExampleStatemachine();");
		System.out.println("	s.setTimer(new TimerService());");
		System.out.println("	RuntimeService.getInstance().registerStatemachine(s, 200);");
		System.out.println("	s.init();\r\n" + "	s.enter();");
		System.out.println("	Scanner scanner = new Scanner(System.in);\r\n" + "	String str;");
		System.out.println("	while((str = scanner.nextLine())!=null){");
		for(Scope scope : scopes) {
			for(Event e : scope.getEvents()) {
				if(!e.equals(scope.getEvents().get(0)))
					System.out.println("		else if(str.equals(\"" + e.getName() + "\")) {");
				else
					System.out.println("		if(str.equals(\"" + e.getName() + "\")) {");
				System.out.println("			s.raise" + e.getName() + "();\r\n" + 
						"			s.runCycle();\r\n" + 
						"			print(s);}");
			}
		}
		System.out.println("		else if(str.equals(\"exit\")) {\r\n" + 
				"			System.exit(0);\r\n" + 
				"		}else {\r\n" + 
				"			System.out.println(\"invalid command\");\r\n" + 
				"}}}");
		System.out.println("	public static void print(IExampleStatemachine s) {");
		for(Scope scope :scopes) {
			for(Property p : scope.getVariables()) {
				System.out.println("		System.out.println(\"" + p.getName() + " = \" + s.getSCInterface().get" + p.getName() + "());");
			}
		}
		System.out.println("}");
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
