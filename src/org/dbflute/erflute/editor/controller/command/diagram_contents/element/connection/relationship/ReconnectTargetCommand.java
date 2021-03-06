package org.dbflute.erflute.editor.controller.command.diagram_contents.element.connection.relationship;

import org.dbflute.erflute.editor.controller.command.AbstractCommand;
import org.dbflute.erflute.editor.model.diagram_contents.element.connection.Relationship;

public class ReconnectTargetCommand extends AbstractCommand {

    private Relationship relation;

    int xp;

    int yp;

    int oldXp;

    int oldYp;

    public ReconnectTargetCommand(Relationship relation, int xp, int yp) {
        this.relation = relation;

        this.xp = xp;
        this.yp = yp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute() {
        this.oldXp = relation.getTargetXp();
        this.oldYp = relation.getTargetYp();

        relation.setTargetLocationp(this.xp, this.yp);
        relation.setParentMove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doUndo() {
        relation.setTargetLocationp(this.oldXp, this.oldYp);
        relation.setParentMove();
    }
}
