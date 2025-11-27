import nullnessAnnotations.relax.*;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import annotations.DetectorUnderTest;
import edu.umd.cs.findbugs.annotations.ExpectWarning;
import edu.umd.cs.findbugs.detect.CheckRelaxingNullnessAnnotation;
@DetectorUnderTest(CheckRelaxingNullnessAnnotation.class)
public class TestNullnessRelaxation {
    static interface I<T extends Number> {
        @Nonnull
        Object get();
        @Nonnull
        Object get2();
        Number set(@CheckForNull Number o);
        @Nonnull
        T set2(@CheckForNull T o);
        @Nonnull
        T set3(@CheckForNull T o);
    }
    static interface SI2 extends I<Integer> {
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        String get();
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        String get2();
        @Override
		@ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION")
        public Integer set(@Nonnull Number o);
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set2(@Nonnull Integer o);
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set3(@Nullable Integer o);
    }
    static class SimpleClazz implements I<Integer> {
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public String get(){ return null; }
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public String get2(){ return null; }
        @Override
		@ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION")
        public Integer set(@Nonnull Number o){ return null; }
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set2(@Nonnull Integer o){ return null; }
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set3(@Nullable Integer o){ return null; }
    }
    static interface SI3 extends I<Integer> {}
    static interface SI4 extends SI3, SI2 {}
    abstract static class Clazz1 implements SI4 {}
    abstract static class Clazz2 extends Clazz1 {}
    static class Clazz extends Clazz2 {
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public String get(){ return null; }
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public String get2(){ return null; }
        @Override
		@ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION")
        public Integer set(@Nonnull Number o){ return null; }
        @Override
		@CheckForNull
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set2(@Nonnull Integer o){ return null; }
        @Override
		@Nullable
        @ExpectWarning("NP_METHOD_PARAMETER_TIGHTENS_ANNOTATION,NP_METHOD_RETURN_RELAXING_ANNOTATION")
        public Integer set3(@Nullable Integer o){ return null; }
    }
}