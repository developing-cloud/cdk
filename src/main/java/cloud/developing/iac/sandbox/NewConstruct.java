package cloud.developing.iac.sandbox;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import software.amazon.awscdk.core.Construct;

public class NewConstruct extends Construct {

	public NewConstruct(Construct scope, String id) {
		super(scope, id);
	}

	@Override
	protected @NotNull List<String> validate() {
		System.out.println("validate");
		return super.validate();
	}

	@Override
	protected void prepare() {
		System.out.println("prepare");
		super.prepare();
	}

}
