package point.android.com.ann_process;

import com.annotation.ApplicationAsyncInit;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
//#org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
//#-Dorg.gradle.debug=true
@AutoService(Processor.class)//注册
@SupportedAnnotationTypes({"com.annotation.ApplicationAsyncInit"})
public class InitProcess extends AbstractProcessor {

    Messager messager;

    @Override
    public synchronized void init(@NonNull ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    //处理注解
    @Override
    public boolean process(@NonNull Set<? extends TypeElement> annotations, @NonNull RoundEnvironment roundEnv) {
        logE("------------------------------------------process");
        //只能获取引入module的注解
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ApplicationAsyncInit.class);
        if(elements ==null || elements.size()<=0){
            return false;
        }
        for (TypeElement typeElement : annotations) {
            logE("anno = " + typeElement.getSimpleName());
        }
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("initAsync")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .returns(boolean.class)
                .addParameter(Context.class, "context")
                .addStatement("boolean flg = false");
        for (Element element : elements) {
            ExecutableElement executableElement = (ExecutableElement) element;
            methodBuilder.addStatement("flg = new $L().$L(context)", executableElement.getEnclosingElement().toString(),
                            executableElement.getSimpleName());
//            logE("class path = " + executableElement.getEnclosingElement().toString());
//            logE("method name = " + executableElement.getSimpleName());
//            logE("return type = " + executableElement.getReturnType().toString());
//            executableElement.getParameters().get(0).asType().toString()
//            logE("param = " + executableElement.getParameters());
//            logE("anno value = " + executableElement.getAnnotation(ApplicationAsyncInit.class).value());
        }
        MethodSpec methodSpec = methodBuilder
                .addStatement("return flg")
                .build();
        logE(methodSpec.toString());
        TypeSpec typeSpec = TypeSpec.classBuilder("ApplicationInit")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        JavaFile javaFile = JavaFile.builder("com.init", typeSpec)
                .build();
        logE("class = " + typeSpec.toString());
        try {
            //　生成文件
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void logE(String data) {
        messager.printMessage(Diagnostic.Kind.NOTE, data);
    }
}
