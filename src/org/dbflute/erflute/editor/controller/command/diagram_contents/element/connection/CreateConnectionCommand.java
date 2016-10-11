package org.dbflute.erflute.editor.controller.command.diagram_contents.element.connection;

import org.dbflute.erflute.editor.model.diagram_contents.element.connection.ConnectionElement;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.ERVirtualTable;

public class CreateConnectionCommand extends AbstractCreateConnectionCommand {

    protected ConnectionElement connection;

    public CreateConnectionCommand(ConnectionElement connection) {
        super();
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() {

        DiagramWalker sourceTable = (DiagramWalker) this.source.getModel();
        DiagramWalker targetTable = (DiagramWalker) this.target.getModel();

        // Table���m�̃����[�V�����́ATable <=> Table �Ōq��

        if (sourceTable instanceof ERVirtualTable) {
            sourceTable = ((ERVirtualTable) sourceTable).getRawTable();
        }
        if (targetTable instanceof ERVirtualTable) {
            targetTable = ((ERVirtualTable) targetTable).getRawTable();
        }

        connection.setSource(sourceTable);
        connection.setTarget(targetTable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doUndo() {
        connection.setSource(null);
        connection.setTarget(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validate() {
        return null;
    }

}
