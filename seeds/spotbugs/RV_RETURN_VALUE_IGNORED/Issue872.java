import ghIssues.*;
import edu.umd.cs.findbugs.annotations.CheckReturnValue;
import org.mockito.Mockito;
public class Issue872 {
	void checkReturnValue() {
		Value checked = new Value();
		checked.checkMe();
	}
	void nse() {
		Value checked = new Value();
		checked.noSideEffect();
	}
	char checkedReturnValue() {
		Value checked = new Value();
		return checked.checkMe().charAt(0);
	}
	char nseUsed() {
		Value checked = new Value();
		return checked.noSideEffect().charAt(0);
	}
	void checkReturnValueMockito() {
		Value checked = new Value();
		Mockito.verify(checked).checkMe();
		Mockito.verify(checked, Mockito.times(4)).checkMe();
	}
	void nseMockito() {
		Value checked = new Value();
		Mockito.verify(checked).noSideEffect();
		Mockito.verify(checked, Mockito.times(4)).noSideEffect();
	}
	void doAnswerMockito() {
		Value spy = Mockito.spy(Value.class);
		Mockito.doAnswer(invocation -> { return "foo"; }
		).when(spy).checkMe();
	}
	void doReturnMockito() {
		Value receiver = Mockito.mock(Value.class);
		Mockito.doReturn("foo").when(receiver).checkMe();
	}
	void doCallRealMethodMockito() {
		Value receiver = Mockito.mock(Value.class);
		Mockito.doCallRealMethod().when(receiver).checkMe();
	}
	void doNothingMockito() {
		Value receiver = Mockito.mock(Value.class);
		Mockito.doNothing().when(receiver).checkMe();
	}
	void doThrowMockito() {
		Value receiver = Mockito.mock(Value.class);
		Mockito.doThrow(Exception.class).when(receiver).checkMe();
	}
	public static class Value {
		@CheckReturnValue
		public String checkMe() { return ""; }
		public String noSideEffect() { return "nse"; }
	}
}