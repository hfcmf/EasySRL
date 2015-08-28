package edu.uw.easysrl.syntax.grammar;

import edu.uw.easysrl.syntax.grammar.Combinator.RuleClass;
import edu.uw.easysrl.syntax.grammar.Combinator.RuleType;

public class NormalForm {

	public static boolean isOk(final RuleClass leftRuleClass, final RuleClass rightRuleClass, final RuleType ruleType,
			@SuppressWarnings("unused") final Category leftCategory,
			@SuppressWarnings("unused") final Category rightCategory) {
		if ((leftRuleClass == RuleClass.FC || leftRuleClass == RuleClass.GFC)
				&& (ruleType == RuleType.FA || ruleType == RuleType.FC || ruleType == RuleType.GFC)) {
			// Eisner normal form constraint.
			return false;
		} else if ((rightRuleClass == RuleClass.BX || rightRuleClass == RuleClass.GBX)
				&& (ruleType == RuleType.BA || ruleType == RuleType.BX || ruleType == RuleType.GBX)) {
			// Eisner normal form constraint.
			return false;
		} else if (leftRuleClass == RuleClass.FORWARD_TYPERAISE && ruleType == RuleType.FA) {
			// Hockenmaier normal form constraint 5a.
			return false;
		} else if (rightRuleClass == RuleClass.BACKWARD_TYPE_RAISE && ruleType == RuleType.BA) {
			// Hockenmaier normal form constraint 5b.
			return false;
		} else if (rightRuleClass == RuleClass.CONJ
				&& (leftRuleClass == RuleClass.FORWARD_TYPERAISE || leftRuleClass == RuleClass.BACKWARD_TYPE_RAISE)) {
			// Hockenmaier normal form constraint 6.
			// Changed to say that coordination cannot be type-raised.
			// TODO breaks for argument cluster coordination
			return false;
		}

		if ((leftRuleClass == RuleClass.FC || leftRuleClass == RuleClass.GFC)
				&& (ruleType == RuleType.FA || ruleType == RuleType.FC)) {
			// Hockenmaier normal form constraint 1a.
			return false;
		}

		if ((rightRuleClass == RuleClass.BX || rightRuleClass == RuleClass.GBX)
				&& (ruleType == RuleType.BA || ruleType == RuleType.BX)) {
			// Hockenmaier normal form constraint 1b.
			return false;
		}

		if ((leftRuleClass == RuleClass.FC) && (ruleType == RuleType.FC || ruleType == RuleType.GFC)) {
			// Hockenmaier normal form constraint 2a.
			return false;
		}

		if ((rightRuleClass == RuleClass.BX) && (ruleType == RuleType.BX || ruleType == RuleType.GBX)) {
			// Hockenmaier normal form constraint 2b.
			return false;
		}

		// I think Hockenmaier normal form 3 only applies if you generalize
		// composition higher than degree 2.

		if ((leftRuleClass == RuleClass.FORWARD_TYPERAISE) && rightRuleClass == RuleClass.GBX
				&& ruleType == RuleType.FC) {
			// Hockenmaier normal form constraint 4a.
			return false;
		}

		if ((rightRuleClass == RuleClass.BACKWARD_TYPE_RAISE) && leftRuleClass == RuleClass.GFC
				&& ruleType == RuleType.BX) {
			// Hockenmaier normal form constraint 4b.
			return false;
		}

		if ((ruleType == RuleType.RP && leftRuleClass != RuleClass.LEXICON && leftRuleClass != RuleClass.LP // Allow
				// LP, for cases like $ 10 .
				)
				|| (ruleType == RuleType.LP && rightRuleClass != RuleClass.LEXICON)) {
			// Force punctuation to go ASAP.
			return false;
		}

		if ((leftRuleClass == RuleClass.LP && ruleType != RuleType.RP) || rightRuleClass == RuleClass.RP) {
			// return false;
		}

		// Constraints to stop you coordinating and composing.
		// Would be much nicer to have slash modalities.
		if (leftRuleClass == RuleClass.CONJ) {
			return false;
		}
		if (rightRuleClass == RuleClass.CONJ && ruleType != RuleType.BA) {
			return false;
		}

		return true;
	}
}