CREATE FUNCTION remove_mismatched_gender_value_function()
RETURNS TRIGGER $$
DECLARE
	pithec_sex varchar(6);
	suggested_sex varchar(6);
BEGIN
	SELECT sex INTO pithec_sex
	FROM Pithecanthropus
	WHERE id = NEW.pithec_id;

	SELECT suggested_sex_of_user INTO suggested_sex
	FROM Invention i
	JOIN Invention_type it ON
	it.invention_id = i.id
	WHERE i.id = NEW.invention_id;

	IF pithec_sex IS NOT NULL AND Suggested_sex IS NOT NULL AND pithec_sex != suggested_sex THEN 
		INSERT INTO ipaLog(
			deleted_action,
			deleted_pithec,
			deleted_invention,
			deleted_pithec_sex,
			suggested_sex,
			reason
		)
		VALUES (
			NEW.action_id,
			NEW.pithec_id,
			NEW.invention_id,
			pithec_sex,
			suggested_sex,
			'Sex of pithecanthropus does not match the recommended one.'
		);

		DELETE FROM Invention_pithec_action
		WHERE id = NEW.id;

		RAISE NOTICE 'This "insert" operation does not match the rule: Pithecanthropus.sex = Invention_id.suggested_sex_of_user.'
	END IF;
	RETURN NULL;
END;

$$ LANGUAGE plpgsql;
