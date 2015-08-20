package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.{ Block => JdtBlock }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.analyzers.JPortConverter
import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings

class Block(node: JdtBlock, protected override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  override val parent = Option(node.getParent).
    map(JPortConverter.convert[ASTNode, AstNode[ASTNode]](_))

  val statements =
    node.statements.asInstanceOf[JavaList[JdtStatement]].to[Array]

  val isEmpty = statements.isEmpty
}
