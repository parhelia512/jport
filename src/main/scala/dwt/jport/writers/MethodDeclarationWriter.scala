package dwt.jport.writers

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ BodyDeclaration => JdtBodyDeclaration }
import org.eclipse.jdt.core.dom.{ MethodDeclaration => JdtMethodDeclaration }

import dwt.jport.ast.MethodDeclaration

object MethodDeclarationWriter extends WriterObject[MethodDeclaration, MethodDeclarationWriter]

class MethodDeclarationWriter extends BodyDeclarationWriter[MethodDeclaration] with TypeParametersWriter[MethodDeclaration] {
  def write(importWriter: ImportWriter, node: MethodDeclaration): Unit = {
    this.node = node
    this.importWriter = importWriter

    if (hasNonEmptyBody(node.prev))
      buffer :+ nl

    writeModifiers
    writeReturnType
    writeName
    writeTypeParameters
    writeParameters
    writeBody

    importWriter :+ node.imports
  }

  def postWrite(): Unit = {
    if (node.hasBody)
      buffer :+ '}'

    buffer :+ nl

    if (hasNonEmptyBody(node.next) || node.next.isDefined && !isMethod(node.next))
      buffer :+ nl
  }

  private def writeReturnType = buffer.append(node.returnType, ' ')
  private def writeName = buffer :+ node.name

  private def writeParameters =
    buffer.append('(').join(node.parameters).append(')')

  private def writeBody = {
    if (!node.hasBody)
      buffer :+ ';'

    else if (node.hasEmptyBody)
      buffer :+ " {"

    else
      buffer.append(nl, '{', nl)
  }

  private def hasNonEmptyBody(node: Option[JdtBodyDeclaration]) =
    node.filter(_.getNodeType == ASTNode.METHOD_DECLARATION).
      map(_.asInstanceOf[JdtMethodDeclaration]).
      filter(e => e.getBody != null && e.getBody.statements.nonEmpty).nonEmpty

  private def isMethod(node: Option[JdtBodyDeclaration]) =
    node.isDefined && node.get.getNodeType == ASTNode.METHOD_DECLARATION
}
