package dwt.jport.ast

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ FieldDeclaration => JdtFieldDeclaration }
import org.eclipse.jdt.core.dom.VariableDeclarationFragment

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.declarations.VariableDeclaration

class FieldDeclaration(node: JdtFieldDeclaration, protected override val visitData: VisitData[JdtBodyDeclaration])
  extends BodyDeclaration(node)
  with VariableDeclaration {

  type JdtNodeType = JdtBodyDeclaration

  override def translatedModifiers = super[VariableDeclaration].translatedModifiers

  protected override def fragments = node.fragments.asInstanceOf[JavaList[VariableDeclarationFragment]]
  protected override def rawType = node.getType
}
