package com.tangotkk.segdialog.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.tangotkk.segdialog.annotation.Click;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by kris on 16/8/21.
 */
@AutoService(Processor.class)
public class ClickBinderProcessor extends AbstractProcessor{

    Messager mMessager;
    Filer mFiler;
    Elements mElementsUtils;



    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Click.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Click.class);

        try {
            TypeSpec type = getClickEventBinderType(elements);
            if (type != null) {
                JavaFile.builder("com.tangotkk.segdialog.holder", type).build().writeTo(mFiler);
            }
        } catch (Exception e) {
            error(e.getMessage());
        }
        return true;
    }

    protected TypeSpec getClickEventBinderType(Set<? extends Element> elements){
        if(elements == null || elements.size() == 0){
            return null;
        }

        TypeElement holderType = mElementsUtils.getTypeElement("com.tangotkk.segdialog.holder.ClickEventBinder");

        for(Element e : elements){
            e.get
        }
    }



    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass,
                                                   Element element) {
        boolean hasError = false;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Verify method modifiers.
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            error(String.format("@%s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        // Verify containing type.
        if (enclosingElement.getKind() != ElementKind.CLASS) {
            error(String.format("@%s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        // Verify containing class visibility is not private.
        if (enclosingElement.getModifiers().contains(Modifier.PRIVATE)) {
            error(String.format("@%s may not be contained in private classes. (%s.%s)",
                    annotationClass.getSimpleName(), enclosingElement.getQualifiedName(),
                    element.getSimpleName()));
            hasError = true;
        }

        return hasError;
    }

    private boolean isBindingInWrongPackage(Class<? extends Annotation> annotationClass,
                                            Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        if (qualifiedName.startsWith("android.")) {
            error(String.format("@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName));
            return true;
        }
        if (qualifiedName.startsWith("java.")) {
            error(String.format("@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName));
            return true;
        }

        return false;
    }

    private boolean isInterface(TypeMirror typeMirror) {
        return typeMirror instanceof DeclaredType
                && ((DeclaredType) typeMirror).asElement().getKind() == ElementKind.INTERFACE;
    }

    private boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (otherType.equals(typeMirror.toString())) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }





    private void error(String errorMessage){
        mMessager.printMessage(Diagnostic.Kind.ERROR, errorMessage);
    }

    private void info(String info){
        mMessager.printMessage(Diagnostic.Kind.NOTE, info);
    }
}
