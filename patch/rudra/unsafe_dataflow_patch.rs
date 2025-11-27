pub fn read<T: io::Read>(input: &mut T) -> Result<Option<Record<'static>>> {
    let mut data = vec![0; 5];

    if let 0 = input.read(&mut data[..1])? {
        return Ok(None);
    }

    input.read_exact(&mut data[1..])?;

    let len = misc::read_dec_5(&*data)?;

    if len < 5 {
        return Err(Error::RecordTooShort(len));
    }

    data.resize(len, 0);
    input.read_exact(&mut data[5..len])?;

    let data_offset = misc::read_dec_5(&data[12..17])?;
    let directory = Directory::parse(&data[24..data_offset])?;

    Ok(Some(Record {
        data: Cow::Owned(data),
        data_offset,
        directory,
    }))
}