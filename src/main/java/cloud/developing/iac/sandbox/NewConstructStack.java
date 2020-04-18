package cloud.developing.iac.sandbox;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;

public class NewConstructStack extends Stack {

	public NewConstructStack(Construct scope, String id) {
		super(scope, id);
		new NewConstruct(this, "newConstruct");
	}

}
