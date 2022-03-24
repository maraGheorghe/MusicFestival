namespace MusicFestivalMPP.validator;

public interface IValidator<TE>
{
    void Validate(TE entity);
}