package cloud.developing.iac.sandbox;

import software.amazon.awscdk.core.App;

public class CdkApp {

	public static void main(String[] args) {
		var app = new App();
		new NewConstructStack(app, "newConstruct");
		app.synth();
	}

}
