import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './accommodation.reducer';
import { IAccommodation } from 'app/shared/model/accommodation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { AccommodationType } from 'app/shared/model/enumerations/accommodation-type.model';
import { AccommodationStatus } from 'app/shared/model/enumerations/accommodation-status.model';

export const AccommodationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const accommodationEntity = useAppSelector(state => state.accommodation.entity);
  const loading = useAppSelector(state => state.accommodation.loading);
  const updating = useAppSelector(state => state.accommodation.updating);
  const updateSuccess = useAppSelector(state => state.accommodation.updateSuccess);
  const accommodationTypeValues = Object.keys(AccommodationType);
  const accommodationStatusValues = Object.keys(AccommodationStatus);
  const handleClose = () => {
    props.history.push('/accommodation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...accommodationEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'Flat',
          status: 'Furnished',
          ...accommodationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testPropertyApp.accommodation.home.createOrEditLabel" data-cy="AccommodationCreateUpdateHeading">
            <Translate contentKey="testPropertyApp.accommodation.home.createOrEditLabel">Create or edit a Accommodation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="accommodation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('testPropertyApp.accommodation.title')}
                id="accommodation-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('testPropertyApp.accommodation.type')}
                id="accommodation-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {accommodationTypeValues.map(accommodationType => (
                  <option value={accommodationType} key={accommodationType}>
                    {translate('testPropertyApp.AccommodationType.' + accommodationType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('testPropertyApp.accommodation.status')}
                id="accommodation-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {accommodationStatusValues.map(accommodationStatus => (
                  <option value={accommodationStatus} key={accommodationStatus}>
                    {translate('testPropertyApp.AccommodationStatus.' + accommodationStatus)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accommodation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AccommodationUpdate;
