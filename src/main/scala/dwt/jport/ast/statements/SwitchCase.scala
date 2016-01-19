package dwt.jport.ast.statements

import scala.collection.JavaConversions._

import org.eclipse.jdt.core.dom.{ SwitchCase => JdtSwitchCase }
import org.eclipse.jdt.core.dom.{ Statement => JdtStatement }

import dwt.jport.analyzers.VisitData
import dwt.jport.ast.Siblings
import dwt.jport.ast.expressions.ExpressionImplicits._

class SwitchCase(node: JdtSwitchCase, private[jport] override val visitData: VisitData[Statement])
  extends Statement(node)
  with Siblings {

  type NodeType = Statement

  val expression = Option(node.getExpression).map(_.toJPort)
  val isDefault = node.isDefault
  val imports = expression.map(_.imports).getOrElse(Seq())
}