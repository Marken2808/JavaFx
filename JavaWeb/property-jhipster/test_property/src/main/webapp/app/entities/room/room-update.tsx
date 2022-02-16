import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAccommodation } from 'app/shared/model/accommodation.model';
import { getEntities as getAccommodations } from 'app/entities/accommodation/accommodation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './room.reducer';
import { IRoom } from 'app/shared/model/room.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { RoomType } from 'app/shared/model/enumerations/room-type.model';

export const RoomUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const accommodations = useAppSelector(state => state.accommodation.entities);
  const roomEntity = useAppSelector(state => state.room.entity);
  const loading = useAppSelector(state => state.room.loading);
  const updating = useAppSelector(state => state.room.updating);
  const updateSuccess = useAppSelector(state => state.room.updateSuccess);
  const roomTypeValues = Object.keys(RoomType);
  const handleClose = () => {
    props.history.push('/room');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAccommodations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...roomEntity,
      ...values,
      accommodation: accommodations.find(it => it.id.toString() === values.accommodation.toString()),
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
          type: 'Attic',
          ...roomEntity,
          accommodation: roomEntity?.accommodation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testPropertyApp.room.home.createOrEditLabel" data-cy="RoomCreateUpdateHeading">
            <Translate contentKey="testPropertyApp.room.home.createOrEditLabel">Create or edit a Room</Translate>
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
                  id="room-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('testPropertyApp.room.title')}
                id="room-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('testPropertyApp.room.acreage')}
                id="room-acreage"
                name="acreage"
                data-cy="acreage"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedBlobField
                label={translate('testPropertyApp.room.image')}
                id="room-image"
                name="image"
                data-cy="image"
                isImage
                accept="image/*"
              />
              <ValidatedField label={translate('testPropertyApp.room.type')} id="room-type" name="type" data-cy="type" type="select">
                {roomTypeValues.map(roomType => (
                  <option value={roomType} key={roomType}>
                    {translate('testPropertyApp.RoomType.' + roomType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('testPropertyApp.room.price')} id="room-price" name="price" data-cy="price" type="text" />
              <ValidatedField
                id="room-accommodation"
                name="accommodation"
                data-cy="accommodation"
                label={translate('testPropertyApp.room.accommodation')}
                type="select"
                required
              >
                <option value="" key="0" />
                {accommodations
                  ? accommodations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/room" replace color="info">
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

export default RoomUpdate;
