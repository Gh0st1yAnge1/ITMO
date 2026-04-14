CREATE TRIGGER check_right_sex
	AFTER INSERT OR UPDATE on Invention_pithec_action
	FOR EACH ROW
	EXECUTE FUNCTION remove_mismatched_gender_values_function();
