import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntity, updateEntity, createEntity, reset } from './property.reducer';
import { IProperty } from 'app/shared/model/property.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Status } from 'app/shared/model/enumerations/status.model';
import { Type } from 'app/shared/model/enumerations/type.model';

export const PropertyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const addresses = useAppSelector(state => state.address.entities);
  const propertyEntity = useAppSelector(state => state.property.entity);
  const loading = useAppSelector(state => state.property.loading);
  const updating = useAppSelector(state => state.property.updating);
  const updateSuccess = useAppSelector(state => state.property.updateSuccess);
  const statusValues = Object.keys(Status);
  const typeValues = Object.keys(Type);
  const handleClose = () => {
    props.history.push('/property');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...propertyEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.address.toString()),
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
          status: 'Sold',
          type: 'House',
          ...propertyEntity,
          address: propertyEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testPropertyApp.property.home.createOrEditLabel" data-cy="PropertyCreateUpdateHeading">
            <Translate contentKey="testPropertyApp.property.home.createOrEditLabel">Create or edit a Property</Translate>
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
                  id="property-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('testPropertyApp.property.title')}
                id="property-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('testPropertyApp.property.image')}
                id="property-image"
                name="image"
                data-cy="image"
                isImage
                accept="image/*"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('testPropertyApp.property.status')}
                id="property-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {translate('testPropertyApp.Status.' + status)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('testPropertyApp.property.type')}
                id="property-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {typeValues.map(type => (
                  <option value={type} key={type}>
                    {translate('testPropertyApp.Type.' + type)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('testPropertyApp.property.acreage')}
                id="property-acreage"
                name="acreage"
                data-cy="acreage"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="property-address"
                name="address"
                data-cy="address"
                label={translate('testPropertyApp.property.address')}
                type="select"
                required
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.street}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/property" replace color="info">
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

export default PropertyUpdate;