package com.cedarsoft.codegen.mock;

import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.ConstructorDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.InterfaceType;
import com.sun.mirror.util.DeclarationVisitor;
import com.sun.mirror.util.SourcePosition;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
class ClassDeclarationMock extends TypeDeclarationMock implements ClassDeclaration {
  public ClassDeclarationMock( Class<?> type ) {
    super( type );
  }

  @Override
  public ClassType getSuperclass() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<ConstructorDeclaration> getConstructors() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<MethodDeclaration> getMethods() {
    throw new UnsupportedOperationException();
  }

  @Override
  public PackageDeclaration getPackage() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getQualifiedName() {
    return type.getClass().getName();
  }

  @Override
  public Collection<TypeParameterDeclaration> getFormalTypeParameters() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<InterfaceType> getSuperinterfaces() {
    Collection<InterfaceType> interfaceTypes = new ArrayList<InterfaceType>();
    for ( Class<?> interfaceType : type.getInterfaces() ) {
      interfaceTypes.add( new InterfaceTypeMock( interfaceType ) );
    }

    return interfaceTypes;
  }

  @Override
  public Collection<FieldDeclaration> getFields() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<TypeDeclaration> getNestedTypes() {
    throw new UnsupportedOperationException();
  }

  @Override
  public TypeDeclaration getDeclaringType() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getDocComment() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<AnnotationMirror> getAnnotationMirrors() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <A extends Annotation> A getAnnotation( Class<A> aClass ) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<Modifier> getModifiers() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSimpleName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public SourcePosition getPosition() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void accept( DeclarationVisitor declarationVisitor ) {
    throw new UnsupportedOperationException();
  }
}
