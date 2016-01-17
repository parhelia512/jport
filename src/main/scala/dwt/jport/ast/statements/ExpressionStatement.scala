package dwt.jport.ast.statements

import org.eclipse.jdt.core.dom.{ ExpressionStatement => JDtExpressionStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.AstNode
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class ExpressionStatement(node: JDtExpressionStatement, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  val expression = node.getExpression.toJPort
  val imports = expression.imports

  override def isMultiline = false
}
