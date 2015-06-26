package dwt.jport.writers.statements

import org.eclipse.jdt.core.dom.ASTNode

import dwt.jport.ast.statements.VariableDeclarationStatement
import dwt.jport.writers.ImportWriter
import dwt.jport.writers.Writer
import dwt.jport.writers.WriterObject

object VariableDeclarationStatementWriter extends WriterObject[VariableDeclarationStatement, VariableDeclarationStatementWriter]

class VariableDeclarationStatementWriter extends Writer[VariableDeclarationStatement] /*with VariableDeclarationWriter[VariableDeclarationStatement]*/ {
  def write(importWriter: ImportWriter, node: VariableDeclarationStatement): Unit = {
    this.node = node
    this.importWriter = importWriter

    writeType
    writeNamesAndInitializers
    buffer += ';'

    importWriter += node.imports
  }

  def postWrite(): Unit = {
    buffer :+ nl

    if (node.next.isDefined) {
      if (!isField(node.next) &&
        !isExpressionStatement(node.next) &&
        !isAdjacentLine(node.next.get))
        buffer :+ nl
    }
  }

  private def writeType = buffer.append(node.typ, ' ')
  private def writeNamesAndInitializers =
    buffer.join(namesInitializers)

  protected def isField(node: Option[ASTNode]) =
    node.isDefined && node.get.getNodeType == ASTNode.FIELD_DECLARATION

  private def namesInitializers: scala.collection.mutable.Buffer[String] =
    node.names.zip(node.initializers).map {
      case (name, value) =>
        val initializer = value.map(_.translate).getOrElse("")
        //val constValue = Expression.toJPort(value, null) //Constant.translate(value)
        if (initializer.isEmpty) name else s"${name} = ${initializer}"
    }
}
